package com.windtempos.tows.render

import com.mojang.blaze3d.buffers.GpuBuffer
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.platform.DepthTestFunction
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.ByteBufferBuilder
import com.mojang.blaze3d.vertex.MeshData
import com.mojang.blaze3d.vertex.MeshData.DrawState
import com.mojang.blaze3d.vertex.VertexFormat
import com.mojang.blaze3d.vertex.VertexFormat.IndexType
import com.windtempos.tows.TalesOfWaywardStars
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MappableRingBuffer
import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.resources.Identifier
import org.joml.Matrix4f
import org.joml.Matrix4fc
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.system.MemoryUtil
import java.util.*
import java.util.function.Supplier


object NPCRenderer {

    private val CLIENT_SIDE_NPC = RenderPipelines.register(
    RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
    .withLocation(Identifier.fromNamespaceAndPath(TalesOfWaywardStars.MOD_ID, "pipeline/client_side_npc"))
    .withDepthTestFunction(DepthTestFunction.EQUAL_DEPTH_TEST) // look into how this works
    .build()
    )

    fun register() {
        TalesOfWaywardStars.LOGGER.info("Registering client-side NPC render pipeline")

        WorldRenderEvents.AFTER_ENTITIES.register(::extractAndDrawNPC)

        TalesOfWaywardStars.LOGGER.info("Registered client-side NPC render pipeline successfully")
    }

    private val allocator = ByteBufferBuilder(RenderType.SMALL_BUFFER_SIZE)
    private var buffer: BufferBuilder? = null

    private fun renderNPC(context: WorldRenderContext) {
        val matrices = context.matrices()
        val camera = context.worldState().cameraRenderState.pos

        matrices.pushPose()
        matrices.translate(-camera.x, -camera.y, -camera.z)

        if (buffer == null) {
            buffer = BufferBuilder(
                allocator,
                CLIENT_SIDE_NPC.vertexFormatMode,
                CLIENT_SIDE_NPC.vertexFormat
            )
        }

        renderFilledBox(matrices.last().pose(), buffer!!, 0f, 100f, 0f, 1f, 101f, 1f, 0f, 1f, 0f, 0.5f)

        matrices.popPose()
    }

    private fun renderFilledBox(positionMatrix: Matrix4fc, buffer: BufferBuilder, minX: Float, minY: Float, minZ: Float, maxX: Float, maxY: Float, maxZ: Float, red: Float, green: Float, blue: Float, alpha: Float) {
		// Front Face
		buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha);

		// Back face
		buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha);

		// Left face
		buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha);

		// Right face
		buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha);

		// Top face
		buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha);

		// Bottom face
		buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha);
		buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha);
	}

    private fun extractAndDrawNPC(ctx: WorldRenderContext) {
        renderNPC(ctx)
        drawNPC(Minecraft.getInstance(), CLIENT_SIDE_NPC)
    }

    private val COLOR_MODULATOR = Vector4f(1f, 1f, 1f, 1f)
    private val MODEL_OFFSET = Vector3f()
    private val TEXTURE_MATRIX = Matrix4f()
    private var vertexBuffer: MappableRingBuffer? = null

    private fun drawNPC(client: Minecraft, pipeline: RenderPipeline) {
        // Build the buffer
        val builtBuffer = buffer!!.buildOrThrow()
        val drawParameters = builtBuffer.drawState()
        val format = drawParameters.format()

        val vertices = upload(drawParameters, format, builtBuffer)

        draw(client, pipeline, builtBuffer, drawParameters, vertices, format)

        // Rotate the vertex buffer so we are less likely to use buffers that the GPU is using
        vertexBuffer!!.rotate()
        buffer = null
    }

    private fun upload(drawParameters: DrawState, format: VertexFormat, builtBuffer: MeshData): GpuBuffer {
        // Calculate the size needed for the vertex buffer
        val vertexBufferSize = drawParameters.vertexCount() * format.vertexSize

        // Initialize or resize the vertex buffer as needed
        if (vertexBuffer == null || vertexBuffer!!.size() < vertexBufferSize) {
            if (vertexBuffer != null) {
                vertexBuffer!!.close()
            }

            vertexBuffer = MappableRingBuffer(
                Supplier { TalesOfWaywardStars.MOD_ID + " example render pipeline" },
                GpuBuffer.USAGE_VERTEX or GpuBuffer.USAGE_MAP_WRITE,
                vertexBufferSize
            )
        }

        // Copy vertex data into the vertex buffer
        val commandEncoder = RenderSystem.getDevice().createCommandEncoder()

        commandEncoder.mapBuffer(
            vertexBuffer!!.currentBuffer().slice(0, builtBuffer.vertexBuffer().remaining().toLong()), false, true
        ).use { mappedView ->
            MemoryUtil.memCopy(builtBuffer.vertexBuffer(), mappedView.data())
        }
        return vertexBuffer!!.currentBuffer()
    }

    private fun draw(
        client: Minecraft,
        pipeline: RenderPipeline,
        builtBuffer: MeshData,
        drawParameters: DrawState,
        vertices: GpuBuffer,
        format: VertexFormat
    ) {
        val indices: GpuBuffer?
        val indexType: IndexType?

        if (pipeline.vertexFormatMode == VertexFormat.Mode.QUADS) {
            // Sort the quads if there is translucency
            builtBuffer.sortQuads(allocator, RenderSystem.getProjectionType().vertexSorting())
            // Upload the index buffer
            indices = pipeline.vertexFormat.uploadImmediateIndexBuffer(builtBuffer.indexBuffer()!!)
            indexType = builtBuffer.drawState().indexType()
        } else {
            // Use the general shape index buffer for non-quad draw modes
            val shapeIndexBuffer = RenderSystem.getSequentialBuffer(pipeline.getVertexFormatMode())
            indices = shapeIndexBuffer.getBuffer(drawParameters.indexCount())
            indexType = shapeIndexBuffer.type()
        }

        // Actually execute the draw
        val dynamicTransforms = RenderSystem.getDynamicUniforms()
            .writeTransform(RenderSystem.getModelViewMatrix(), COLOR_MODULATOR, MODEL_OFFSET, TEXTURE_MATRIX)
        RenderSystem.getDevice()
            .createCommandEncoder()
            .createRenderPass(
                Supplier { TalesOfWaywardStars.MOD_ID + " example render pipeline rendering" },
                client.mainRenderTarget.getColorTextureView()!!,
                OptionalInt.empty(),
                client.mainRenderTarget.getDepthTextureView(),
                OptionalDouble.empty()
            ).use { renderPass ->
                renderPass.setPipeline(pipeline)
                RenderSystem.bindDefaultUniforms(renderPass)
                renderPass.setUniform("DynamicTransforms", dynamicTransforms)

                // Bind texture if applicable:
                // Sampler0 is used for texture inputs in vertices
                // renderPass.bindTexture("Sampler0", textureSetup.texure0(), textureSetup.sampler0());
                renderPass.setVertexBuffer(0, vertices)
                renderPass.setIndexBuffer(indices, indexType)

                // The base vertex is the starting index when we copied the data into the vertex buffer divided by vertex size
                @Suppress("KotlinConstantConditions")
                renderPass.drawIndexed(0 / format.vertexSize, 0, drawParameters.indexCount(), 1)
            }
        builtBuffer.close()
    }

    @JvmStatic
    fun close() {
        allocator.close()

        if (vertexBuffer != null) {
            vertexBuffer!!.close()
            vertexBuffer = null
        }
    }
}