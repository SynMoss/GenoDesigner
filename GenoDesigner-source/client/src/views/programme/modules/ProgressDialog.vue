<template>
  <el-dialog
    :close-on-click-modal="false"
    :visible.sync="visible"
    :show-close="false"
    width="300px"
  >
    <div slot="title" style="display:flex;justify-content:space-between">
      <div>Loading...</div>
      <i class="el-icon-close" style="cursor: pointer;" @click="cancel"></i>
    </div>
    <div style="text-align:center">
      <el-progress type="circle" :percentage="percentage"></el-progress>
    </div>
  </el-dialog>
</template>
<script>
export default {
  props: {
    percentage: {
      type: Number,
      default: function(params) {
        return 0
      }
    }
  },
  data() {
    return {
      visible: false,
      isCancel:false
    }
  },
  methods: {
    show(){
      this.visible = true
    },
    cancel(){
      this.$emit('cancel')
      this.close()
    },
    close(){
      this.visible = false
    }
  },

}
</script>
<style scoped>
</style>