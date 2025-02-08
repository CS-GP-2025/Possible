package com.example.possible.ui.profile.children

import DialogBuilder
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.possible.databinding.ActivityChildrenBinding
import com.example.possible.model.Child
import com.example.possible.repo.local.database.LocalRepoImp
import com.example.possible.util.adapter.ChildrenAdapter
import com.example.possible.util.listener.ChildListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class Children : AppCompatActivity(), ChildListener {
    private lateinit var db: LocalRepoImp
    private lateinit var binding: ActivityChildrenBinding
    private lateinit var childrenList: ArrayList<Child>
    private lateinit var adapter: ChildrenAdapter

    private val code = 1001 // Request code for permission

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChildrenBinding.inflate(layoutInflater)
        childrenList = ArrayList()
        adapter = ChildrenAdapter(childrenList, this)
        adapter.setTestMode(false)
        setContentView(binding.root)

        db = LocalRepoImp(this)
        enableEdgeToEdge()
        checkAndRequestPermissions()
        getChildren { setUpAdapter(childrenList) }
        binding.backArrowIV.setOnClickListener {
            finish()
        }

    }

    private fun getChildren(onView:()->Unit) {
        lifecycleScope.launch(Dispatchers.IO) {
            childrenList = db.getAllChildren() as ArrayList<Child>
            withContext(Dispatchers.Main) {
                if (childrenList.isEmpty()) {
                    binding.recyclerView.visibility = android.view.View.GONE
                    binding.hint.visibility = android.view.View.VISIBLE
                } else { // Set adapter to RecyclerView
                    binding.recyclerView.visibility = android.view.View.VISIBLE
                    binding.hint.visibility = android.view.View.GONE
                    onView()
                }
            }
        }
    }

    private fun setUpAdapter(list: ArrayList<Child>) {
        adapter.updateData(list)
        binding.recyclerView.adapter = adapter
    }


    private val perReqCode = 100

    private fun checkAndRequestPermissions() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(READ_MEDIA_IMAGES)
        } else {
            arrayOf(READ_EXTERNAL_STORAGE)
        }

        // فحص الأذونات دفعة واحدة وطلبها إذا لزم الأمر
        ActivityCompat.requestPermissions(this, permissions, perReqCode)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == perReqCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == code && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            if (selectedImageUri != null) {
                // تعيين الصورة في ImageView
                //binding.profileIV.setImageURI(selectedImageUri)

                //  saveProfileImageUri(selectedImageUri.toString())
            }
        }
    }

    override fun onClick(child: Child) {
        // Display the URI when an item is clicked
        val intent = Intent(this, AddChildActivity::class.java)
        intent.putExtra("mode", "edit")
        intent.putExtra("childId", child.id)
        startActivity(intent)
    }

    override fun onDelete(position: Int, child: Child) {
        DialogBuilder.showAlertDialog(this, "Delete Child",
            "Are you sure you want to delete this child?"
        ,"Yes","No",
            {
                Toast.makeText(this, "Deleted child at position ${position+1}", Toast.LENGTH_SHORT).show()
                lifecycleScope.launch(Dispatchers.IO) {
                    db.deleteChild(child)
                    withContext(Dispatchers.Main) {
                        getChildren{
                            adapter.onDelete(position,childrenList)
                        }
                    }
                }
            },
            {

            }
        )
    // Handle item deletion

    }

    override fun onResume() {
        super.onResume()
        getChildren{setUpAdapter(childrenList)}
    }
}
