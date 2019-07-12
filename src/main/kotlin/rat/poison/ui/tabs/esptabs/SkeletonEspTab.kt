package rat.poison.ui.tabs.esptabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisCheckBox
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.boolToStr
import rat.poison.curSettings
import rat.poison.strToBool
import rat.poison.ui.changed

class SkeletonEspTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val skeletonEsp = VisCheckBox("Skeleton Esp")

    val showTeam = VisCheckBox("Show Team")
    val showEnemies = VisCheckBox("Show Enemies")

    init {
        //Create Skeleton ESP Toggle
        Tooltip.Builder("Whether or not to enable skeleton esp").target(skeletonEsp).build()
        if (curSettings["SKELETON_ESP"]!!.strToBool()) skeletonEsp.toggle()
        skeletonEsp.changed { _, _ ->
            curSettings["SKELETON_ESP"] = skeletonEsp.isChecked.boolToStr()
            true
        }

        //Create Show Team Toggle
        Tooltip.Builder("Whether or not to show team with esp").target(showTeam).build()
        if (curSettings["SKELETON_SHOW_TEAM"]!!.strToBool()) showTeam.toggle()
        showTeam.changed { _, _ ->
            curSettings["SKELETON_SHOW_TEAM"] = showTeam.isChecked.boolToStr()
            true
        }

        //Create Show Enemies Toggle
        Tooltip.Builder("Whether or not to show enemies with esp").target(showEnemies).build()
        if (curSettings["SKELETON_SHOW_ENEMIES"]!!.strToBool()) showEnemies.toggle()
        showEnemies.changed { _, _ ->
            curSettings["SKELETON_SHOW_ENEMIES"] = showEnemies.isChecked.boolToStr()
            true
        }

        table.padLeft(25F)
        table.padRight(25F)

        table.add(skeletonEsp).left().row()
        table.add(showTeam).left()
        table.add(showEnemies).padLeft(200 - skeletonEsp.width).left()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Skeleton"
    }
}