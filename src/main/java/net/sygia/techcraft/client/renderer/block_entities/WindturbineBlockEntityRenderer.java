package net.sygia.techcraft.client.renderer.block_entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.sygia.techcraft.Techcraft;
import net.sygia.techcraft.block_entities.WindturbineBlockEntity;
import net.sygia.techcraft.models.ModelWindTurbine;
import net.minecraft.resources.ResourceLocation;

public class WindturbineBlockEntityRenderer implements BlockEntityRenderer<WindturbineBlockEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Techcraft.MOD_ID, "textures/block/windturbine_texture.png");
    private final ModelWindTurbine<?> model;
    private float anglee = 0;

    public WindturbineBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new ModelWindTurbine<>(context.bakeLayer(ModelWindTurbine.LAYER_LOCATION));
    }

    @Override
    public void render(WindturbineBlockEntity entity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        anglee += 1f;

        poseStack.pushPose();
        poseStack.translate(0.5, 2.5, 0.5);
        poseStack.scale(1.0F, -1.0F, -1.0F);

        VertexConsumer builder = bufferSource.getBuffer(model.renderType(TEXTURE));
        model.VoxelShapes.render(poseStack, builder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

        poseStack.pushPose();
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(anglee));
        model.fan.render(poseStack, builder, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        poseStack.popPose();

        poseStack.popPose();
    }
}
