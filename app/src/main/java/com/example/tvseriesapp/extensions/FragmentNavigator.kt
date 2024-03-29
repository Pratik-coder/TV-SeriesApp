package com.example.tvseriesapp.extensions

import android.annotation.SuppressLint
import android.content.Intent
import android.util.SparseArray
import androidx.core.util.forEach
import androidx.core.view.forEachIndexed
import androidx.core.view.get
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.tvseriesapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView



fun BottomNavigationView.setUpWithNavController(
    navGraphIds:List<Int>,
    fragmentManager: FragmentManager,
    containerId: Int,
    intent: Intent
):LiveData<NavController>{

val graphIdToTagMap=SparseArray<String>()
    val selectedNavController=MutableLiveData<NavController>()

    var firstFragmentGraphId=0
    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag= getFragmentTag(index)

        val navHostFragmet= obtainNavHostFragment(fragmentManager,
        fragmentTag,
        navGraphId,
        containerId)
        val graphId=navHostFragmet.navController.graph.id
        if (index==0){
            firstFragmentGraphId=graphId
        }
       graphIdToTagMap[graphId]=fragmentTag
        if (this.selectedItemId==graphId){
            selectedNavController.value=navHostFragmet.navController
            attachNavFragment(fragmentManager,navHostFragmet,index==0)
        }
        else{
            detachNavFragment(fragmentManager,navHostFragmet)
        }

      var selectedItemTag=graphIdToTagMap[this.selectedItemId]
      var firstFragmentTag=graphIdToTagMap[firstFragmentGraphId]
      var isOnFirstFragment=selectedItemTag==firstFragmentTag
        setOnNavigationItemSelectedListener { item ->
            // Don't do anything if the state is state has already been saved.
            if (fragmentManager.isStateSaved) {
                false
            } else {
                val newlySelectedItemTag = graphIdToTagMap[item.itemId]
                if (selectedItemTag != newlySelectedItemTag) {
                    // Pop everything above the first fragment (the "fixed start destination")
                    fragmentManager.popBackStack(firstFragmentTag,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                            as NavHostFragment

                    // Exclude the first fragment tag because it's always in the back stack.
                    if (firstFragmentTag != newlySelectedItemTag) {
                        // Commit a transaction that cleans the back stack and adds the first fragment
                        // to it, creating the fixed started destination.
                        fragmentManager.beginTransaction()
                            .setCustomAnimations(
                                androidx.navigation.ui.R.anim.nav_default_enter_anim,
                                androidx.navigation.ui.R.anim.nav_default_exit_anim,
                                androidx.navigation.ui.R.anim.nav_default_pop_enter_anim,
                                androidx.navigation.ui.R.anim.nav_default_pop_exit_anim
                                )
                            .attach(selectedFragment)
                            .setPrimaryNavigationFragment(selectedFragment)
                            .apply {
                                // Detach all other Fragments
                                graphIdToTagMap.forEach { _, fragmentTagIter ->
                                    if (fragmentTagIter != newlySelectedItemTag) {
                                        detach(fragmentManager.findFragmentByTag(firstFragmentTag)!!)
                                    }
                                }
                            }
                            .addToBackStack(firstFragmentTag)
                            .setReorderingAllowed(true)
                            .commit()
                    }
                    selectedItemTag = newlySelectedItemTag
                    isOnFirstFragment = selectedItemTag == firstFragmentTag
                    selectedNavController.value = selectedFragment?.navController
                    true
                } else {
                    false
                }
            }
        }

        // Optional: on item reselected, pop back stack to the destination of the graph
        setUpItemReselected(graphIdToTagMap, fragmentManager)

        // Handle deep link
        setUpDeepLink(navGraphIds, fragmentManager, containerId, intent)

        // Finally, ensure that we update our BottomNavigationView when the back stack changes
        fragmentManager.addOnBackStackChangedListener {
            if (!isOnFirstFragment && !fragmentManager.isOnBackStack(firstFragmentTag)) {
                this.selectedItemId = firstFragmentGraphId
            }

            // Reset the graph if the currentDestination is not valid (happens when the back
            // stack is popped after using the back button).
            selectedNavController.value?.let { controller ->
                if (controller.currentDestination == null) {
                    controller.navigate(controller.graph.id)
                }
            }
        }
    }
    return selectedNavController
}

private fun BottomNavigationView.setUpDeepLink(
    navGraphIds:List<Int>,
    fragmentManager: FragmentManager,
    containerId: Int,
    intent: Intent
){
      navGraphIds.forEachIndexed { index, navGraphId ->
          val fragmentTag= getFragmentTag(index)
          val navHostFragment= obtainNavHostFragment(
              fragmentManager,
              fragmentTag,
              navGraphId,
              containerId
          )
           if (navHostFragment.navController.handleDeepLink(intent)
               && selectedItemId!=navHostFragment.navController.graph.id)
           {
               this.selectedItemId=navHostFragment.navController.graph.id
           }
      }
}

private fun BottomNavigationView.setUpItemReselected(
    graphIdToTagMap:SparseArray<String>,
    fragmentManager: FragmentManager
){
  setOnNavigationItemReselectedListener {item->
      val newlySelectedItemTag=graphIdToTagMap[item.itemId]
      val selectedFragment=fragmentManager.findFragmentByTag(newlySelectedItemTag) as NavHostFragment?
      val navController=selectedFragment?.navController
      navController?.popBackStack(navController.graph.startDestination,false)
  }
}

private fun detachNavFragment(fragmentManager: FragmentManager,
navHostFragment: NavHostFragment){
    fragmentManager.beginTransaction()
        .detach(navHostFragment)
        .commitNow()
}

private fun attachNavFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment,
    isPrimaryFragment:Boolean
){
    fragmentManager.beginTransaction()
        .attach(navHostFragment)
        .apply {
            if (isPrimaryFragment){
               setPrimaryNavigationFragment(navHostFragment)
            }
        }.commitNow()
}

private fun obtainNavHostFragment(
    fragmentManager: FragmentManager,
    fragmentTag:String,
    navGraphId:Int,
    containerId:Int
):NavHostFragment{
   val existingFragment=fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
    existingFragment?.let { return it }

   val navHostFragment=NavHostFragment.create(navGraphId)
      fragmentManager.beginTransaction()
          .add(containerId, navHostFragment, fragmentTag)
          .commitNow()

    return navHostFragment
}

private fun FragmentManager.isOnBackStack(backStackName:String):Boolean {
    val backStackCount = backStackEntryCount
    for (index in 0 until backStackCount) {
        if (getBackStackEntryAt(index).name == backStackName) {
            return true
        }
    }
    return false
}

private fun getFragmentTag(index:Int)="bottomNavigation#$index"
