<template>
  <div>
    <el-dialog
      v-bind="$attrs"
      :close-on-click-modal="false"
      :modal-append-to-body="false"
      v-on="$listeners"
      @open="onOpen"
      @close="onClose"
    >
      <el-row :gutter="0">
        <el-form
          ref="elForm"
          :model="formData"
          :rules="rules"
          size="small"
          label-width="100px"
        >
          <el-col :span="24">
            <el-form-item
              label="Option name"
              prop="label"
            >
              <el-input
                v-model="formData.label"
                placeholder="Please enter the option name"
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item
              label="Option value"
              prop="value"
            >
              <el-input
                v-model="formData.value"
                placeholder="Please enter the option value"
                clearable
              >
                <el-select
                  slot="append"
                  v-model="dataType"
                  :style="{width: '100px'}"
                >
                  <el-option
                    v-for="(item, index) in dataTypeOptions"
                    :key="index"
                    :label="item.label"
                    :value="item.value"
                    :disabled="item.disabled"
                  />
                </el-select>
              </el-input>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer">
        <el-button
          type="primary"
          @click="handleConfirm"
        >
          Confirm
        </el-button>
        <el-button @click="close">
          Cancel
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { isNumberStr } from '@/utils/index'

export default {
  components: {},
  inheritAttrs: false,
  props: [],
  data() {
    return {
      id: 100,
      formData: {
        label: undefined,
        value: undefined
      },
      rules: {
        label: [
          {
            required: true,
            message: 'Please enter the option name',
            trigger: 'blur'
          }
        ],
        value: [
          {
            required: true,
            message: 'Please enter the option value',
            trigger: 'blur'
          }
        ]
      },
      dataType: 'string',
      dataTypeOptions: [
        {
          label: 'string',
          value: 'string'
        },
        {
          label: 'number',
          value: 'number'
        }
      ]
    }
  },
  computed: {},
  watch: {
        'formData.value': function (val) {
      this.dataType = isNumberStr(val) ? 'number' : 'string'
    }
  },
  created() {},
  mounted() {},
  methods: {
    onOpen() {
      this.formData = {
        label: undefined,
        value: undefined
      }
    },
    onClose() {},
    close() {
      this.$emit('update:visible', false)
    },
    handleConfirm() {
      this.$refs.elForm.validate(valid => {
        if (!valid) return
        if (this.dataType === 'number') {
          this.formData.value = parseFloat(this.formData.value)
        }
        this.formData.id = this.id++
        this.$emit('commit', this.formData)
        this.close()
      })
    }
  }
}
</script>
