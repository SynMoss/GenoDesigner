<template>
      <el-dialog
        :show-close="false"
      :close-on-click-modal="false"
      :title="language==='zh'?zh_list.AAr:'Select Sequence'"
      :visible.sync="visible"
      :width="width"
      append-to-body
    >
    <vxe-list class="choose" height="300" :data="sequenceList">
      <template #default="{ items }">
        <div @click="choose(item)" :class="item.id===currentId?'left left-choose':'left'" v-for="(item) in items" :key="item.id">{{ item.name }}</div>
      </template>
    </vxe-list>
    <div slot="footer" class="dialog-footer">
        <el-button
          type="primary"
          @click="submitForm"
          >{{language==='zh'?zh_list.confirmA:'OK'}}</el-button
        >
      </div>
      </el-dialog>
</template>
<script>
import { zh_list } from "@/data/constant";
export default {
  props:{
  },
  data() {
    return {
      zh_list,
      language: process.env.VUE_APP_LANGUAGE,
      width:'500px',
      visible: false,
      currentId: null,
      currentName: null,
      sequenceList: []
    }
  },
  computed: {},
  created() {
  },
  mounted() {},

  methods: {
    async test(){
            },
    changePart(index){
            this.model = this.sequenceList[index]
    },
    show(sequenceList) {
      this.sequenceList = sequenceList
      this.currentId = this.sequenceList[0].id
      this.currentName = this.sequenceList[0].name
      this.visible = true
    },
    choose(item) {
      this.currentId = item.id
      this.currentName = item.name
    },
    submitForm(){
      this.$emit('ok',{id:this.currentId,name:this.currentName})
      this.visible = false
    },
        cancel() {
      this.$emit('ok',{id:this.sequenceList[0].id,name:this.sequenceList[0].name})
      this.visible = false
    },
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/styles/variables.scss";
.choose{
  cursor: pointer;
  background-color:#fff;padding: 0;border:1px solid #eee;
}
.left{
  white-space: nowrap;
  text-overflow:ellipsis;
  overflow:hidden;
  padding: 5px 10px;
  margin: 5px;
}
.left-choose{
  background-color: rgb(235, 241,255);
  color: $base-color;

}
.ove-dialog-header{
    background-color: #ebf1f5;
    height: 100%;
    width: 100%;
    padding-top: 18px;
    padding-left:20px;
  }
.window-size{
  overflow: auto;
  height: 300px;
}
</style>
