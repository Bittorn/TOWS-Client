package com.windtempos.tows.ink

import com.bladecoder.ink.runtime.Story
import com.windtempos.tows.TalesOfWaywardStars
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier

class InkRunner(pathToStory: String) {

    var story: Story? = null
    var storyId: Identifier? = null

    var globalTags: HashMap<String?, String?> = HashMap()

    var speaker: Component = Component.literal("Placeholder speaker")
    var tooltip: Component = Component.literal("Placeholder tooltip")

    var line: Component = Component.literal("Placeholder line")

    init { // (pathToStory: String, screen: DialogScreen)
//        dialogScreen = screen
        storyId = Identifier.fromNamespaceAndPath(TalesOfWaywardStars.MOD_ID, pathToStory)

        // Creating Story object from JSON
        try {
//            story = StoryRegistry.getOrEmpty(storyId).orElseThrow()
//            story!!.resetState()
        } catch (e: Exception) {
            TalesOfWaywardStars.LOGGER.error("Error parsing Story: {}", storyId, e)
            throw RuntimeException(e)
        }

        loadTags()
        parseTags()
    }

    private fun loadTags() {
        try {
            val tags = story!!.globalTags
            for (tag in tags) {
                val splitTag: Array<String?> = tag.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                globalTags[splitTag[0]!!.trim { it <= ' ' }] = splitTag[1]!!.trim { it <= ' ' }
            }
        } catch (e: Exception) {
            TalesOfWaywardStars.LOGGER.error("Error loading Story tags: {}", storyId, e)
        }
    }

    private fun parseTags() {
        if (globalTags.containsKey("speaker")) speaker = Component.translatable(globalTags["speaker"]!!)
        if (globalTags.containsKey("tooltip")) tooltip = Component.translatable(globalTags["tooltip"]!!)
    }

    fun selectChoice(index: Int) {
        try {
            story!!.chooseChoiceIndex(index)
            // Needed twice to skip the repeating of your choice
            // Yes it's stupid, but it works, so it's not stupid
            continueOrExitStory()
            continueOrExitStory()
        } catch (e: Exception) {
            TalesOfWaywardStars.LOGGER.error("Error making choice in Story: {}", storyId, e)
            throw RuntimeException(e)
        }
    }

    fun continueOrExitStory() {
        try {
            if (story!!.canContinue()) {
                line = Component.translatable(story!!.Continue().trim { it <= ' ' })
                if (story!!.getCurrentChoices().isNotEmpty()) {
                    val choices = HashMap<Int?, String?>()
                    for (choice in story!!.getCurrentChoices()) {
                        choices[choice.index] = choice.text.trim { it <= ' ' }
                    }
//                    dialogScreen.setChoices(choices)
                }
            } else if (story!!.getCurrentChoices().isEmpty()) {
                exitStory()
            }
        } catch (e: Exception) {
            TalesOfWaywardStars.LOGGER.error("Error continuing Story: {}", storyId, e)
            throw RuntimeException(e)
        }
    }

    fun exitStory() {
//        dialogScreen.close()
    }
    
}