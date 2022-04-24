package ru.yurii.testingworkshopapp.screen

import android.view.View
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher
import ru.yurii.testingworkshopapp.R
import ru.yurii.testingworkshopapp.projectlist.ChooseProjectDialogFragment

object ChooseProjectScreen : KScreen<ChooseProjectScreen>() {
    override val layoutId: Int = R.layout.choose_project_dialog_fragment
    override val viewClass: Class<*> = ChooseProjectDialogFragment::class.java

    val list = KRecyclerView({ withId(R.id.list) }, { itemType { ProjectItem(it) } })

    class ProjectItem(parent: Matcher<View>) : KRecyclerItem<ProjectItem>(parent) {
        val title = KTextView { withId(R.id.title) }
    }
}
