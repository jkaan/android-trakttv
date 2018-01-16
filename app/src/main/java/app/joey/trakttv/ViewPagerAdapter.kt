package app.joey.trakttv

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
  private val fragments: MutableList<Fragment> = mutableListOf()

  override fun getItem(position: Int): Fragment {
    return fragments.get(position)
  }

  override fun getCount(): Int {
    return fragments.size
  }

  fun addFragment(fragment: Fragment) {
    fragments.add(fragment)
  }
}
