package scisrc.mobiledev.ecommercelayout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import scisrc.mobiledev.ecommercelayout.R
import scisrc.mobiledev.ecommercelayout.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ตั้งค่าข้อมูลโปรไฟล์ (ตัวอย่าง)
        binding.profileImage.setImageResource(R.drawable.ic_user_avatar) // รูปโปรไฟล์
        binding.profileName.text = "Nogging eiei"
        binding.profileEmail.text = "user@email.com"
        binding.profileAddress.text = "123/4 หมู่ 5 ถนนตัวอย่าง ตำบล/แขวง ตัวอย่าง เขต/อำเภอ จังหวัด 12345"

        // เพิ่มข้อมูลใหม่
        binding.profileIdCard.text = "รหัสบัตรประชาชน: 1234567890123"  // รหัสบัตรประชาชน
        binding.profilePhone.text = "เบอร์โทร: 012-345-6789"  // เบอร์ติดต่อ
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
