package scisrc.mobiledev.ecommercelayout

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import scisrc.mobiledev.ecommercelayout.databinding.ActivityMainBinding
import scisrc.mobiledev.ecommercelayout.ui.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar


        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("Favorites", MODE_PRIVATE)

        // Handle bottom navigation item clicks (Updated)
        val bottomNav: BottomNavigationView = binding.bottomNavigation
        bottomNav.setOnItemSelectedListener { menuItem ->
            val selectedFragment = when (menuItem.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_profile -> ProfileFragment()
                R.id.nav_booking -> BookingFragment()
                else -> null
            }

            selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
            }

            true
        }

        // Load default fragment (Home)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
            bottomNav.selectedItemId = R.id.nav_home
        }
    }

    // ✅ ฟังก์ชันบันทึกสินค้าที่ถูกใจลง SharedPreferences
    fun updateFavorites(favorites: Set<String>) {
        sharedPreferences.edit().putStringSet("favorite_items", favorites).apply()
    }

    // ✅ ฟังก์ชันดึงรายการสินค้าที่ถูกใจจาก SharedPreferences
    fun getFavorites(): Set<String> {
        return sharedPreferences.getStringSet("favorite_items", emptySet()) ?: emptySet()
    }
}
