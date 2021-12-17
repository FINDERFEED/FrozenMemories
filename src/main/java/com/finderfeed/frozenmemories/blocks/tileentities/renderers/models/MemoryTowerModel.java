package com.finderfeed.frozenmemories.blocks.tileentities.renderers.models;// Made with Blockbench 4.0.5
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.finderfeed.frozenmemories.FrozenMemories;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class MemoryTowerModel extends Model {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(FrozenMemories.MOD_ID, "memory_tower"), "main");
	private final ModelPart tower_base;
	private final ModelPart rotating_part;
	private final ModelPart floating_part;

	public MemoryTowerModel(ModelPart root) {
		super(RenderType::text);
		this.tower_base = root.getChild("tower_base");
		this.rotating_part = root.getChild("rotating_part");
		this.floating_part = root.getChild("floating_part");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition tower_base = partdefinition.addOrReplaceChild("tower_base", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -28.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(64, 0).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition rotating_part = partdefinition.addOrReplaceChild("rotating_part", CubeListBuilder.create().texOffs(0, 98).addBox(8.0F, -45.0F, -5.0F, 2.0F, 20.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 98).addBox(-10.0F, -45.0F, -5.0F, 2.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = rotating_part.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 98).addBox(8.0F, -10.0F, -5.0F, 2.0F, 20.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 98).addBox(-10.0F, -10.0F, -5.0F, 2.0F, 20.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -35.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition floating_part = partdefinition.addOrReplaceChild("floating_part", CubeListBuilder.create().texOffs(112, 110).addBox(-2.0F, -47.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}



	public void renderToBuffer(PoseStack matrices, VertexConsumer buffer,float partialTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		tower_base.render(matrices, buffer, packedLight, packedOverlay);
		matrices.pushPose();
		matrices.mulPose(Vector3f.YP.rotationDegrees(
				(Minecraft.getInstance().level.getGameTime() + partialTicks) % 360));
		rotating_part.render(matrices, buffer, packedLight, packedOverlay);
		matrices.popPose();
		floating_part.render(matrices, buffer, packedLight, packedOverlay);
	}

	@Override
	public void renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_, float p_103115_, float p_103116_, float p_103117_, float p_103118_) {

	}
}