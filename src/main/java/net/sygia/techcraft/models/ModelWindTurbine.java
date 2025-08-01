package net.sygia.techcraft.models;// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.sygia.techcraft.Techcraft;

public class ModelWindTurbine<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Techcraft.MOD_ID, "modelwindturbine"), "main");
	public final ModelPart VoxelShapes;
	public final ModelPart fan;

	public ModelWindTurbine(ModelPart root) {
		this.VoxelShapes = root.getChild("VoxelShapes");
		this.fan = root.getChild("fan");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition VoxelShapes = partdefinition.addOrReplaceChild("VoxelShapes", CubeListBuilder.create().texOffs(24, 9).addBox(-1.0F, -23.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(8, 0).addBox(-3.0F, 21.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(8, 17).addBox(-2.0F, 19.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(8, 9).addBox(-2.0F, -24.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.0F, -24.0F, -1.0F, 2.0F, 48.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 16.0F, 0.0F));

		PartDefinition fan = partdefinition.addOrReplaceChild("fan", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = fan.addOrReplaceChild("cube_r1", CubeListBuilder.create()
						.texOffs(8, 24).addBox(-0.75F, 0.0F, -0.375F, 1.5F, 10.0F, 0.75F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r2 = fan.addOrReplaceChild("cube_r2", CubeListBuilder.create()
						.texOffs(8, 24).addBox(-0.75F, 0.0F, -0.375F, 1.5F, 10.0F, 0.75F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r3 = fan.addOrReplaceChild("cube_r3", CubeListBuilder.create()
						.texOffs(8, 24).addBox(-0.75F, 0.0F, -0.375F, 1.5F, 10.0F, 0.75F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		VoxelShapes.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		fan.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}