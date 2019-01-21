package com.mvc.cryptovault_android.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * code : 200
 * data : {"balance":10000,"baseTokenId":4,"baseTokenName":"USDT","content":"dfs","id":8,"incomeMax":5,"incomeMin":1,"limitValue":100,"minValue":1,"name":"cctv","purchased":0,"ratio":1,"rule":"dfsdf","startAt":1547710446692,"stopAt":1548777600000,"times":11,"tokenId":1,"tokenName":"VRT","userLimit":10}
 * message :
 */
data class FinancialDetailBean(
        var code: Int,
        var data: DataBean,
        var message: String
) {
    /**
     * balance : 10000
     * baseTokenId : 4
     * baseTokenName : USDT
     * content : dfs
     * id : 8
     * incomeMax : 5
     * incomeMin : 1
     * limitValue : 100
     * minValue : 1
     * name : cctv
     * purchased : 0
     * ratio : 1
     * rule : dfsdf
     * startAt : 1547710446692
     * stopAt : 1548777600000
     * times : 11
     * tokenId : 1
     * tokenName : VRT
     * userLimit : 10
     */
    data class DataBean(
            var balance: Double,
            var baseTokenId: Int,
            var baseTokenName: String,
            var content: String,
            var id: Int,
            var incomeMax: Int,
            var incomeMin: Int,
            var limitValue: Int,
            var minValue: Int,
            var name: String,
            var purchased: Int,
            var ratio: Int,
            var rule: String,
            var startAt: Long,
            var stopAt: Long,
            var times: Int,
            var tokenId: Int,
            var tokenName: String,
            var userLimit: Int
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readDouble(),
                parcel.readInt(),
                parcel.readString(),
                parcel.readString(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readString(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readString(),
                parcel.readLong(),
                parcel.readLong(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readString(),
                parcel.readInt())

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeDouble(balance)
            parcel.writeInt(baseTokenId)
            parcel.writeString(baseTokenName)
            parcel.writeString(content)
            parcel.writeInt(id)
            parcel.writeInt(incomeMax)
            parcel.writeInt(incomeMin)
            parcel.writeInt(limitValue)
            parcel.writeInt(minValue)
            parcel.writeString(name)
            parcel.writeInt(purchased)
            parcel.writeInt(ratio)
            parcel.writeString(rule)
            parcel.writeLong(startAt)
            parcel.writeLong(stopAt)
            parcel.writeInt(times)
            parcel.writeInt(tokenId)
            parcel.writeString(tokenName)
            parcel.writeInt(userLimit)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<DataBean> {
            override fun createFromParcel(parcel: Parcel): DataBean {
                return DataBean(parcel)
            }

            override fun newArray(size: Int): Array<DataBean?> {
                return arrayOfNulls(size)
            }
        }
    }
}
