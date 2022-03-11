package com.wahyuindra.crudfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_update_data.*

class UpdateData : AppCompatActivity() {

    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var cekNIM: String? = null
    private var cekNama: String? = null
    private var cekJurusan: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)
        supportActionBar!!.title = "Update Data"

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        data
        update.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                cekNIM = new_nim.getText().toString()
                cekNama = new_nama.getText().toString()
                cekJurusan = new_jurusan.getText().toString()

                if (isEmpty(cekNIM!!) || isEmpty(cekNama!!) ||
                    isEmpty(cekJurusan!!)) {
                    Toast.makeText(
                        this@UpdateData,
                        "Data tidak boleh ada yang kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    val setMahasiswa = data_mahasiswa()
                    setMahasiswa.nim = new_nim.getText().toString()
                    setMahasiswa.nama = new_nama.getText().toString()
                    setMahasiswa.jurusan = new_jurusan.getText().toString()
                    updateMahasiswa(setMahasiswa)
                }
            }
        })
    }

    // Mengecek apakah ada data yang kosong, sebelum diupdate
    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }

    //Menampilkan data yang akan di update
    private val data: Unit

    private get() {
    //Menampilkan data dari item yang dipilih sebelumnya
            val getNIM = intent.extras!!.getString("dataNIM")
            val getNama = intent.extras!!.getString("dataNama")
            val getJurusan = intent.extras!!.getString("dataJurusan")
            new_nim!!.setText(getNIM)
            new_nama!!.setText(getNama)
            new_jurusan!!.setText(getJurusan)
        }

    //Proses Update data yang sudah ditentukan
    private fun updateMahasiswa(mahasiswa: data_mahasiswa) {
        val userID = auth!!.uid
        val getKey = intent.extras!!.getString("getPrimaryKey")
        database!!.child("Admin")
            .child(userID!!)
            .child("Mahasiswa")
            .child(getKey!!)
            .setValue(mahasiswa)
            .addOnSuccessListener {
                new_nim!!.setText("")
                new_nama!!.setText("")
                new_jurusan!!.setText("")
                Toast.makeText(this@UpdateData, "Data Berhasil diubah",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
    }

}