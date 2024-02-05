<template>
  <div class="insert-dialog">
    <el-dialog
      :close-on-click-modal="false"
      :title="language==='zh'?zh_list.aAw:'Advanced Search'"
      :visible.sync="visible"
      width="600px"
      append-to-body
    >
      <div>
        <el-radio-group v-model="radio" style="width: 100%">
          <el-radio label="a"
            >{{language==='zh'?zh_list.AAm:'Search by Sequence in Fasta Format'}}</el-radio
          >
          <div class="upload-fasta" style="margin-top: 15px">
            <el-input  disabled v-model="fileName">
              <el-button
              slot="append"
              class="lookFile"
              @click="toUpload"
              type="primary"
              :disabled="(radio == 'c'||radio =='b')"
              :loading="uploadLoading"
              style="color: white;background-color: #3d892e;"
            >
              {{language==='zh'?zh_list.AAn:'Load Files'}}
            </el-button>



              >

            </el-input>
          </div>
          <div class="selectfeatuer">
            <el-radio label="c" @click="disButton">{{language==='zh'?zh_list.AAo:'Search by Feature Name'}}</el-radio>
            <el-input
              style="margin-top: 15px"
              :clearable="true"
              v-model="featureName"
              :disabled="radio !== 'c'"
            />
          </div>
          <div class="textfind">
            <el-radio label="b">{{language==='zh'?zh_list.AAp:'Search by Sequence'}}</el-radio>
            <el-input
              style="margin-top: 15px"
              type="textarea"
              :clearable="true"
              v-model="content"
              resize="none"
              :autosize="{ minRows: 4, maxRows: 10 }"
              :disabled="radio !== 'b'"
            />
          </div>
        </el-radio-group>
        <input
          @click.stop
          class="insert-upload"
          ref="uploadPart"
          type="file"
          accept=".fa,.fas,.fasta"
          @change="uploadPart"
        />
      </div>
      <div slot="footer">
        <div>
          <el-button @click="cancel">{{language==='zh'?zh_list.cancelA:'Cancel'}}</el-button>
          <el-button
            type="primary"
            @click="submitForm"
            :disabled="subLoading"
            :loading="subLoading"
            >{{language==='zh'?zh_list.confirmA:'OK'}}</el-button
          >
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { zh_list } from "@/data/constant";
import { search } from "@/api/system/sequence";
import { ONLY_Char_ARRAY } from '@/data/constant'
export default {
  name: "InsertDialog",
  props: {
    sequenceId: {
      type: Number,
      default: function (params) {
        return null;
      },
    },
  },
  data() {
    return {
      zh_list,
      language: process.env.VUE_APP_LANGUAGE,
      fileName: null,
      radio: "a",
      disabled: true,
      allAlign: null,
      subLoading: false,
      getLoading: false,
      visible: false,
      model: {},
      content: null,
      featureName: null,
      modelDefault: {},
      tableLoading: false,
      input: "",
      uploadLoading: false,
      disableButton: false,
      file: null,
    };
  },
  computed: {},
  created() {},
  methods: {
    disButton() {
      this.disableButton = true;
      console.log("this.disableButton:" + this.disableButton);
    },
    toUpload() {
      this.$refs.uploadPart.click();
    },
        uploadPart(e) {
      const file = e.target.files[0];
      this.fileName = file.name;
      this.file = file;
    },
    show() {
      this.content = null;
      this.visible = true;
    },
    cancel() {
      this.visible = false;
    },
    submitForm() {
      const formData = new FormData();
      formData.append("id", this.sequenceId);
      if (this.radio === "a") {
        if (!this.file) {
          this.$message.warning(this.zh_list.AAq);
          return;
        }
        this.subLoading = true;
        formData.append("file", this.file);
      }
      if (this.radio === "b") {
        if (!this.content) {
          this.$message.warning("Please input");
          return;
        }
        for (const j in this.content) {
          if (ONLY_Char_ARRAY.indexOf(this.content[j]) === -1) {
            this.$message.warning(this.language==='zh'?'Please input'+ONLY_Char_ARRAY.toString():"Can only input "+ONLY_Char_ARRAY.toString());
            return;
          }
        }
        this.subLoading = true;
        formData.append("str", this.content);
      }
      if (this.radio === "c") {
        if (!this.featureName) {
          this.$message.warning("Please input");
          return;
        }
        this.subLoading = true;
        formData.append("featureName", this.featureName);
      }
      search(formData)
        .then((res) => {
          this.$refs.uploadPart.value = "";
          this.visible = false;
          this.$emit("ok", res.data);
        })
        .finally((err) => {
          this.subLoading = false;
        });
    },
  },
};
</script>
<style lang="scss" >
.insert-upload {
  display: none;
}

.upload-fasta {
  display: flex;
}
.textfind {
  margin-top: 30px;
}

.selectfeatuer {
  margin-top: 30px;
}
.lookFile {
  background-color: yellow;
  color: green;
}
</style>
