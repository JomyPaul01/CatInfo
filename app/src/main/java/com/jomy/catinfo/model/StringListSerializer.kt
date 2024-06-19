package com.jomy.catinfo.model

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.jomy.catinfo.StringList
import java.io.InputStream
import java.io.OutputStream

/**
 *  class to represent the serializer for String list data stored in the datastore
 */
class StringListSerializer : Serializer<StringList> {
    override val defaultValue: StringList = StringList.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): StringList {
        try {
            return StringList.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: StringList, output: OutputStream) = t.writeTo(output)
}