package com.example.lmsandroid

import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri


class CourseProvider : ContentProvider() {
    companion object {
        val PROVIDER_NAME = "com.example.lmsandroid.CourseProvider"
        val URL = "content://$PROVIDER_NAME/course"
        const val uriCode = 1

        // parsing the content URI
        val CONTENT_URI = Uri.parse(URL)
        var uriMatcher: UriMatcher? = null

        const val Description = "description"
        const val NAME = "name"
        const val TABLE_NAME = "course1"
        private val COURSE_PROJECTION_MAP: HashMap<String, String>? = null


        init {

            // to match the content URI
            // every time user access table under content provider
            uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            // to access whole table
            uriMatcher!!.addURI(
                PROVIDER_NAME,
                "course",
                uriCode
            )

            // to access a particular row
            // of the table
            uriMatcher!!.addURI(
                PROVIDER_NAME,
                "course/*",
                uriCode
            )
        }


        private var db: SQLiteDatabase? = null


    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val rowID = db!!.insert(TABLE_NAME, "", values)

        /**
         * If record is added successfully
         */

        if (rowID > 0) {
            val _uri = ContentUris.withAppendedId(CONTENT_URI, rowID)
            context!!.contentResolver.notifyChange(_uri, null)
            return _uri
        }

        throw SQLException("Failed to add a record into $uri")
    }


    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        var sortOrder = sortOrder
        val qb = SQLiteQueryBuilder()
        qb.tables = TABLE_NAME
        when (uriMatcher!!.match(uri)) {
            uriCode -> qb.projectionMap = COURSE_PROJECTION_MAP
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        if (sortOrder == null || sortOrder === "") {
            sortOrder = NAME
        }
        val c = qb.query(
            db, projection, selection, selectionArgs, null,
            null, sortOrder
        )
        c.setNotificationUri(context!!.contentResolver, uri)
        return c
    }

    override fun onCreate(): Boolean {
        val context = context
        val dbHelper = context?.let { DatabaseHelper(it) }
        if (dbHelper != null) {
            db = dbHelper.getWritableDatabase()
        }
        return if (db == null) false else true
    }


    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        var count = 0
        count = when (uriMatcher!!.match(uri)) {
            uriCode -> db!!.update(TABLE_NAME, values, selection, selectionArgs)
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        var count = 0
        count = when (uriMatcher!!.match(uri)) {
            uriCode -> db!!.delete(TABLE_NAME, selection, selectionArgs)
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher!!.match(uri)) {
            uriCode -> "vnd.android.cursor.dir/course"
            else -> throw IllegalArgumentException("Unsupported URI: $uri")
        }
    }

    public class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "CourseDB", null, 1) {
        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(
                "create table ${TABLE_NAME} (" +
                        "_id integer primary key autoincrement, " +
                        "${NAME})"
            )
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            if (db != null) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
            };

            onCreate(db);
        }
    }

}