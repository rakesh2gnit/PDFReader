package com.hcl.pdfreader

import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.*
import com.krishna.fileloader.FileLoader
import com.krishna.fileloader.listener.FileRequestListener
import com.krishna.fileloader.pojo.FileResponse
import com.krishna.fileloader.request.FileLoadRequest
import java.io.File

class ViewActivity : AppCompatActivity() {

    lateinit var pdfView: PDFView
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        pdfView = findViewById(R.id.pdf_viewer)
        progressBar = findViewById(R.id.progress_bar)

        if (intent != null) {
            val viewType = intent.getStringExtra("ViewType")
            if (viewType != null || TextUtils.isEmpty(viewType)) {
                if (viewType == "assets") {
                    pdfView.fromAsset("test.pdf")
                        .password(null)//enter password if PDF is password protected
                        .defaultPage(0)//set the default page
                        .enableSwipe(true)//enable the swipe to change page
                        .swipeHorizontal(false)//set horizontal swipe to false
                        .enableDoubletap(true)//double tap to zoom
                        .onDraw(object : OnDrawListener {
                            override fun onLayerDrawn(
                                canvas: Canvas,
                                pageWidth: Float,
                                pageHeight: Float,
                                displayedPage: Int
                            ) {
                            }
                        })
                        .onDrawAll(object : OnDrawListener {
                            override fun onLayerDrawn(
                                canvas: Canvas,
                                pageWidth: Float,
                                pageHeight: Float,
                                displayedPage: Int
                            ) {
                            }
                        })
                        .onPageError(object : OnPageErrorListener {
                            override fun onPageError(page: Int, t: Throwable) {
                                Toast.makeText(this@ViewActivity, "Error", Toast.LENGTH_LONG).show()
                            }
                        })
                        .onPageChange(object : OnPageChangeListener {
                            override fun onPageChanged(page: Int, pageCount: Int) {
                            }
                        })
                        .onTap(object : OnTapListener {
                            override fun onTap(e: MotionEvent): Boolean {
                                return true
                            }
                        })
                        .onRender(object : OnRenderListener {
                            override fun onInitiallyRendered(
                                nbPages: Int,
                                pageWidth: Float,
                                pageHeight: Float
                            ) {
                                pdfView.fitToWidth()
                            }
                        })
                        .enableAnnotationRendering(true)
                        .invalidPageColor(Color.WHITE)
                        .load()
                }

                else if (viewType == "storage") {
                    val pdfFile = Uri.parse(intent.getStringExtra("FileUri"))
                    pdfView.fromUri(pdfFile)
                        .password(null)
                        .defaultPage(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .onDraw(object : OnDrawListener {
                            override fun onLayerDrawn(
                                canvas: Canvas,
                                pageWidth: Float,
                                pageHeight: Float,
                                displayedPage: Int
                            ) {
                            }
                        })
                        .onDrawAll(object : OnDrawListener {
                            override fun onLayerDrawn(
                                canvas: Canvas,
                                pageWidth: Float,
                                pageHeight: Float,
                                displayedPage: Int
                            ) {
                            }
                        })
                        .onPageError(object : OnPageErrorListener {
                            override fun onPageError(page: Int, t: Throwable) {
                                Toast.makeText(this@ViewActivity, "Error", Toast.LENGTH_LONG).show()
                            }
                        })
                        .onPageChange(object : OnPageChangeListener {
                            override fun onPageChanged(page: Int, pageCount: Int) {
                            }
                        })
                        .onTap(object : OnTapListener {
                            override fun onTap(e: MotionEvent): Boolean {
                                return true
                            }
                        })
                        .onRender(object : OnRenderListener {
                            override fun onInitiallyRendered(
                                nbPages: Int,
                                pageWidth: Float,
                                pageHeight: Float
                            ) {
                                pdfView.fitToWidth()
                            }
                        })
                        .enableAnnotationRendering(true)
                        .invalidPageColor(Color.WHITE)
                        .load()
                }

                else if (viewType == "internet") {
                    progressBar.visibility = View.VISIBLE
                    FileLoader.with(this)
                        .load("https://github.github.com/training-kit/downloads/github-git-cheat-sheet.pdf")
                        .fromDirectory("PDFFiles", FileLoader.DIR_EXTERNAL_PUBLIC)
                        .asFile(object : FileRequestListener<File> {
                            override fun onLoad(
                                fileLoadRequest: FileLoadRequest,
                                fileResponse: FileResponse<File>
                            ) {
                                progressBar.visibility = View.GONE
                                val pdfFile = fileResponse.getBody()
                                pdfView.fromFile(pdfFile)
                                    .password(null)
                                    .defaultPage(0)
                                    .enableSwipe(true)
                                    .swipeHorizontal(false)
                                    .enableDoubletap(true)
                                    .onDraw(object : OnDrawListener {
                                        override fun onLayerDrawn(
                                            canvas: Canvas,
                                            pageWidth: Float,
                                            pageHeight: Float,
                                            displayedPage: Int
                                        ) {
                                        }
                                    })
                                    .onDrawAll(object : OnDrawListener {
                                        override fun onLayerDrawn(
                                            canvas: Canvas,
                                            pageWidth: Float,
                                            pageHeight: Float,
                                            displayedPage: Int
                                        ) {
                                        }
                                    })
                                    .onPageError(object : OnPageErrorListener {
                                        override fun onPageError(page: Int, t: Throwable) {
                                            Toast.makeText(
                                                this@ViewActivity,
                                                "Error",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    })
                                    .onPageChange(object : OnPageChangeListener {
                                        override fun onPageChanged(page: Int, pageCount: Int) {
                                        }
                                    })
                                    .onTap(object : OnTapListener {
                                        override fun onTap(e: MotionEvent): Boolean {
                                            return true
                                        }
                                    })
                                    .onRender(object : OnRenderListener {
                                        override fun onInitiallyRendered(
                                            nbPages: Int,
                                            pageWidth: Float,
                                            pageHeight: Float
                                        ) {
                                            pdfView.fitToWidth()
                                        }
                                    })
                                    .enableAnnotationRendering(true)
                                    .invalidPageColor(Color.WHITE)
                                    .load()
                            }

                            override fun onError(
                                fileLoadRequest: FileLoadRequest,
                                throwable: Throwable
                            ) {
                                Toast.makeText(this@ViewActivity, "Error", Toast.LENGTH_LONG).show()
                                progressBar.setVisibility(View.GONE)
                            }
                        })
                }
            }
        }
    }
}