package com.hcl.pdfreader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    val PICK_PDF_CODE = 1000
    lateinit var btn_open_assets: Button//for assets
    lateinit var btn_open_storage:Button//for phone storage
    lateinit var btn_opn_intenet: Button//for internet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_open_assets = findViewById(R.id.open_assets)
        btn_open_storage = findViewById(R.id.open_storage)

        btn_open_assets.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view:View) {
                val intent = Intent(this@MainActivity, ViewActivity::class.java)
                intent.putExtra("ViewType", "assets")
                startActivity(intent)
            }
        })

        btn_open_storage.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view:View) {
                val browseStorage = Intent(Intent.ACTION_GET_CONTENT)
                browseStorage.setType("application/pdf")
                browseStorage.addCategory(Intent.CATEGORY_OPENABLE)
                startActivityForResult(Intent.createChooser(browseStorage, "Select PDF"), PICK_PDF_CODE)
            }
        })
    }

    override protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null)
        {
            val selectedPdf = data.getData()
            val intent = Intent(this@MainActivity, ViewActivity::class.java)
            intent.putExtra("ViewType", "storage")
            intent.putExtra("FileUri", selectedPdf.toString())
            startActivity(intent)
        }
    }
}
