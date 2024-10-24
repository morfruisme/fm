package fruit.lle.entity;

import fruit.lle.FmSounds;
import fruit.lle.item.FmItem;
import fruit.lle.networking.MudballPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class MudballEntity extends ThrownItemEntity {
    public <E extends MudballEntity> MudballEntity(EntityType<E> type, World world) {
		super(type, world);
	}

    public MudballEntity(World world, LivingEntity owner) {
		super(FmEntity.MUDBALL, owner, world);
	}

    public MudballEntity(World world, double x, double y, double z) {
        super(FmEntity.MUDBALL, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return FmItem.MUDBALL;
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            ItemStack stack = this.getStack();
            ParticleEffect particleEffect = !stack.isEmpty() && stack.isOf(this.getDefaultItem()) ?
                new ItemStackParticleEffect(ParticleTypes.ITEM, stack) :
                // TODO: mud particle
                ParticleTypes.ITEM_SNOWBALL;

            for(int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult result) {
        super.onEntityHit(result);
        Entity target = result.getEntity();
        target.damage(this.getDamageSources().thrown(this, this.getOwner()), 0);

        if (target instanceof ServerPlayerEntity player)
            ServerPlayNetworking.send(player, new MudballPayload());

        if (!this.getWorld().isClient())
            this.getWorld().playSound(null, target.getBlockPos(), FmSounds.MUDBALL_SPLASH, SoundCategory.AMBIENT, 1f, 1f);
    }

    @Override
    protected void onCollision(HitResult result) {
        super.onCollision(result);
        if (!this.getWorld().isClient()) {
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }
}