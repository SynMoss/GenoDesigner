<template>
  <div class="insert-dialog">
    <el-dialog
      :close-on-click-modal="false"
      :title="language==='zh'?zh_list.replaceA:'Replace'"
      :visible.sync="visible"
      width="1000px"
      append-to-body
    >
      <div>
        <el-form
          label-position="top"
          v-loading="getLoading"
          ref="form"
          :model="model"
          :rules="rules"
          label-width="80px"
          size="small"
        >
          <el-form-item :label="language==='zh'?zh_list.AAf:'Position'">
            <el-input
              v-model="value2"
              size="mini"
              :placeholder="language==='zh'?zh_list.aAx:'Format 1..123'"
              clear
              class="programmer-search-input"
              style="width: 200px"
            >
            </el-input>
          </el-form-item>
          <el-form-item :label="language==='zh'?zh_list.AAh:'Original Sequence'">
            <template slot="label">
              <span
                >{{language==='zh'?zh_list.AAh:'Original Sequence'}}
                <i class="el-icon-document-copy copy-icon" @click="copySequence"></i>
              </span>
            </template>
            <el-input
              disabled
              type="textarea"
              :clearable="true"
              v-model="sourceSequence"
              ref="sequence"
              rows="10"
            />
          </el-form-item>
          <el-form-item :label="language==='zh'?zh_list.AAg:'New Sequence'" prop="content">
            <el-input
              type="textarea"
              :clearable="true"
              v-model="model.content"
              rows="10"
              resize="none"
            />
          </el-form-item>
          <el-form-item>
            <el-checkbox v-model="strand">Reverse complement</el-checkbox>
          </el-form-item>
        </el-form>
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
import { ONLY_Char_ARRAY } from "@/data/constant";
export default {
  name: 'ReplaceDialog',
  props: {
    bpLength: {
      type: Number,
      default: function(params) {
        return 0
      }
    }
  },
  data() {
    const checkContent = (rule, value, callback) => {
      if (!value) {
        return callback(new Error(process.env.VUE_APP_LANGUAGE==='zh'?'The Replacement Content Cannot be Empty':'The Replacement Content Cannot be Empty'))
      }
      for (const j in value) {
        if (ONLY_Char_ARRAY.indexOf(value[j]) === -1) {
          return callback(new Error(process.env.VUE_APP_LANGUAGE==='zh'?'Can Only Input'+ONLY_Char_ARRAY.toString():'Can Only Input '+ONLY_Char_ARRAY.toString()))
        }
      }
      callback()
    }
    return {
      zh_list,
      strand: false,
      language: process.env.VUE_APP_LANGUAGE,
      value2: '',
      subLoading: false,
      getLoading: false,
      visible: false,
      content: null,
      input: '',
      rules: {
        content: [{ validator: checkContent, trigger: 'blur' }]
      },
      model: {
        content: ''
      },
      sourceSequence: ''
    }
  },
  computed: {},
  created() {},
  methods: {
    show({ sourceSequence, position }) {
      this.sourceSequence = sourceSequence
      this.value2 = position
      this.model.content = ''
      this.strand = false
      this.visible = true
    },
    cancel() {
      this.visible = false
    },
    submitForm() {
      if (!this.value2) {
        this.$message.warning('Please Enter the Correct Range')
        return
      }
      if (!/^\d+\.{2}\d+$/.test(this.value2)) {
        this.$message.warning('Please Enter the Correct Range')
        return
      }
      const arr = this.value2.split('..')
      const start = +arr[0]
      const end = +arr[1]
      if (start > end) {
        this.$message.warning('Please Enter the Correct Range')
        return
      }
      if (end > this.bpLength) {
        this.$message.warning('Please Enter the Correct Range')
        return
      }
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$emit('ok', { content: this.model.content, start:start-1, end:end,strand: this.strand })
          this.visible = false
        }
      })
    },
    copySequence() {
      let OrderNumber = this.sourceSequence;
      let newInput = document.createElement("input");
      newInput.value = OrderNumber;
      document.body.appendChild(newInput);
      newInput.select();
      document.execCommand("Copy");
      newInput.remove();
      this.$message({
        message: "Copy Success",
        type: "success",
      });
    }
  }
}
</script>
<style lang="scss" scoped>
.insert-dialog-footer {
  display: flex;
  justify-content: space-between;
}
.design-list-top {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  .design-list-top-right {
    flex: 0 0 240px;
  }
}
.copy-icon:hover{
  cursor: pointer
}

</style>
