package com.membattle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Севастьян on 15.10.2017.
 */

public class GetMem {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("data")
        @Expose
        private List<Datum> data = null;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }


}
