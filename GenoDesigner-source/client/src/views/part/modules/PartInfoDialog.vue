<template>
  <div>
    <el-dialog
      :close-on-click-modal="true"
      :title="language==='zh'?this.zh_list.a:'Information'"
      :visible.sync="partVisible"
      width="700px"
      append-to-body
    >
      <div class="table-f">
        <el-descriptions direction='vertical' :column="1" class="margin-top" height="100%" border>
          <el-descriptions-item :label="language==='zh'?zh_list.L:'Name'" labelStyle="font-weight:600;color: #333333;">{{rowData.name}}</el-descriptions-item>
          <el-descriptions-item :label="language==='zh'?zh_list.N:'Type'" labelStyle="font-weight:600;color: #333333;">{{rowData.type}}</el-descriptions-item>
          <el-descriptions-item :label="language==='zh'?zh_list.b:'Description'" labelStyle="font-weight:600;color: #333333;">{{rowData.description}}</el-descriptions-item>
          <el-descriptions-item :label="language==='zh'?zh_list.Q:'Content'" labelStyle="font-weight:600;color: #333333;">{{rowData.content}}</el-descriptions-item>
      </el-descriptions>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button
          type="primary"
          @click="closeDiaLog"
          >{{language==='zh'?zh_list.L:'Cancel'}}</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { zh_list } from "@/data/constant";
export default {
  name:'PartInfoDialog',
  data() {
    return {
      zh_list,
      language: process.env.VUE_APP_LANGUAGE,
      subLoading: false,
      partVisible: false,
      rowData:{},
    };
  },
  computed: {},
  created() {},
  methods: {
        openDialog(row) {
      this.rowData = row
      console.log(row)
      this.partVisible = true;
    },
        closeDiaLog() {
      this.partVisible = false;
    },
  },
};
</script>
<style scoped>
.table-f {
  height: calc(100vh - 300px);
  overflow: auto;
}
</style>
