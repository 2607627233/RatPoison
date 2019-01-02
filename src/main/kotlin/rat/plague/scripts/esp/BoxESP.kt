

package rat.plague.scripts.esp

import com.badlogic.gdx.graphics.Color
import rat.plague.game.entity.*
import rat.plague.game.entity.EntityType.Companion.ccsPlayer
import rat.plague.game.entityByType
import rat.plague.game.forEntities
import rat.plague.game.me
import rat.plague.game.worldToScreen
import rat.plague.overlay.RatPlagueOverlay
import rat.plague.settings.BOX_ESP
import rat.plague.settings.*
import rat.plague.utils.Vector

private val vHead = Vector()
private val vFeet = Vector()

private val vTop = Vector(0.0, 0.0, 0.0)
private val vBot = Vector(0.0, 0.0, 0.0)

private val boxes = Array(128) { Box() }

private var currentIdx = 0

internal fun boxEsp() = RatPlagueOverlay {
	if (!BOX_ESP || !ENABLE_ESP) return@RatPlagueOverlay
	
	forEntities(ccsPlayer) {
		val entity = it.entity
		if (entity == me || entity.dead() || entity.dormant()) return@forEntities false
		
		vHead.set(entity.bone(0xC), entity.bone(0x1C), entity.bone(0x2C) + 9)
		vFeet.set(vHead.x, vHead.y, vHead.z - 75)
		
		if (worldToScreen(vHead, vTop) && worldToScreen(vFeet, vBot)) {
			val boxH = vBot.y - vTop.y
			val boxW = boxH / 5F
			
			val bomb: Entity = entityByType(EntityType.CC4)?.entity ?: -1
			val c = if (bomb > 0 && entity == bomb.carrier()) Color.GREEN
			else if (me.team() == entity.team()) Color.BLUE else Color.RED
			
			val sx = (vTop.x - boxW).toInt()
			val sy = vTop.y.toInt()
			
			boxes[currentIdx].apply {
				x = sx
				y = sy
				w = Math.ceil(boxW * 2).toInt()
				h = boxH.toInt()
				color = c
			}
			
			currentIdx++
		}
		
		false
	}
	
	shapeRenderer.apply sR@ {
		begin()
		for (i in 0..currentIdx - 1) boxes[i].apply {
			this@sR.color = color
			rect(x.toFloat(), y.toFloat(), w.toFloat(), h.toFloat())
		}
		end()
	}
	
	currentIdx = 0
}

private data class Box(var x: Int = -1, var y: Int = -1,
                       var w: Int = -1, var h: Int = -1,
                       var color: Color = Color.WHITE)