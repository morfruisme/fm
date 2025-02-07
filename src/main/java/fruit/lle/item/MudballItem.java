package fruit.lle.item;

import fruit.lle.entity.MudballEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class MudballItem extends Item implements ProjectileItem {
    public MudballItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // snowball sound
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL,
            0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));

        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient) {
            MudballEntity mudball = new MudballEntity(world, user);
            mudball.setItem(stack);
            mudball.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(mudball);
        }

        stack.decrementUnlessCreative(1, user);
        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        MudballEntity mudball = new MudballEntity(world, pos.getX(), pos.getY(), pos.getZ());
        mudball.setItem(stack);
        return mudball;
    }
}