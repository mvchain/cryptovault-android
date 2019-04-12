package com.mvc.ttpay_android.bean

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
            var incomeMax: Double,
            var incomeMin: Double,
            var limitValue: Double,
            var minValue: Double,
            var name: String,
            var purchased: Double,
            var ratio: Double,
            var rule: String,
            var sold: Double,
            var startAt: Long,
            var stopAt: Long,
            var times: Int,
            var tokenId: Int,
            var tokenName: String,
            var userLimit: Double
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readDouble(),
                parcel.readInt(),
                parcel.readString(),
                parcel.readString(),
                parcel.readInt(),
                parcel.readDouble(),
                parcel.readDouble(),
                parcel.readDouble(),
                parcel.readDouble(),
                parcel.readString(),
                parcel.readDouble(),
                parcel.readDouble(),
                parcel.readString(),
                parcel.readDouble(),
                parcel.readLong(),
                parcel.readLong(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readString(),
                parcel.readDouble()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeDouble(balance)
            parcel.writeInt(baseTokenId)
            parcel.writeString(baseTokenName)
            parcel.writeString(content)
            parcel.writeInt(id)
            parcel.writeDouble(incomeMax)
            parcel.writeDouble(incomeMin)
            parcel.writeDouble(limitValue)
            parcel.writeDouble(minValue)
            parcel.writeString(name)
            parcel.writeDouble(purchased)
            parcel.writeDouble(ratio)
            parcel.writeString(rule)
            parcel.writeDouble(sold)
            parcel.writeLong(startAt)
            parcel.writeLong(stopAt)
            parcel.writeInt(times)
            parcel.writeInt(tokenId)
            parcel.writeString(tokenName)
            parcel.writeDouble(userLimit)
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
