<template>
    <el-dialog
      :close-on-click-modal="false"
      :title="language==='zh'?'Message':'Message'"
      :visible.sync="visible"
      width="1000px"
      append-to-body
      @close="cancel"
    >
      <div>
        <el-radio-group style="margin-bottom:10px;" v-model="queryParams.isRead" @change="changeRead">
            <el-radio :label="0">{{language==='zh'?'Unread':'Unread'}}</el-radio>
            <el-radio :label="1">{{language==='zh'?'Read':'Read'}}</el-radio>
        </el-radio-group>
        <vxe-table
          ref="xTable"
          border="inner"
          class="table-self"
          header-cell-class-name="table-header-self"
          cell-class-name="table-cell-self"
          :data="tableData"
          :loading="tableLoading"
          :seq-config="{
            startIndex:
              (this.tablePage.currentPage - 1) * this.tablePage.pageSize,
          }"
        >
          <vxe-column field="noticeTitle" :title="language==='zh'?'Title':'Title'"></vxe-column>
          <vxe-column field="createTime" width="200" :title="language==='zh'?'Creation time':'Creation time'"></vxe-column>
          <vxe-column
            width="80"
            field="isRead"
            :title="language==='zh'?'Read/Unread':'Read/Unread'"
            :filter-config="{ remote: true }"
          >
            <template #default="{ row }">
                <span style="color:green;" v-if="row.isRead">{{language==='zh'?'Read':'Read'}}</span>
                <span style="color:red;" v-else>{{language==='zh'?'Unread':'Unread'}}</span>
            </template>
          </vxe-column>
          <vxe-column field="j" :title="language==='zh'?'Operation':'Operation'" width="150">
            <template #default="{ row }">
              <a
                @click="showInfo(row)"
                type="text"
                size="small"
                class="table-opera"
                >{{language==='zh'?'View':'View'}}</a
              >
            </template>
          </vxe-column>
        </vxe-table>
        <div class="pager-flex">
          <div class="pager-flex-total" >
            <template v-if="language==='zh'">A Total Of Data Was Searched {{ tablePage.totalResult }} Items</template>
            <template v-else>Found {{ tablePage.totalResult }} data item in total</template>
          </div>
          <vxe-pager
            class-name="pager-self"
            size="small"
            border
            :loading="tableLoading"
            :current-page="tablePage.currentPage"
            :page-size="tablePage.pageSize"
            :total="tablePage.totalResult"
            :layouts="[
              'PrevPage',
              'JumpNumber',
              'NextPage',
              'Sizes',
              'FullJump',
            ]"
            @page-change="pageChangeEvent"
          >
          </vxe-pager>
        </div>
      </div>
      <div slot="footer">
        <el-button @click="cancel">{{language==='zh'?'Close':'Close'}}</el-button>
      </div>
    </el-dialog>
</template>
<script>
import XEUtils from 'xe-utils'
import { getAction,putAction } from "@/api/manage";
export default {
  data() {
    return {
      language: process.env.VUE_APP_LANGUAGE,
      tablePage: {
        currentPage: 1,
        pageSize: 10,
        totalResult: 0
      },
      queryParams: {isRead: 0},
      visible: false,
      tableData: [],
      tableLoading: false,
    }
  },
  computed: {},
  created() {},
  methods: {
    changeRead(){
        this.tablePage.currentPage = 1
      this.tablePage.pageSize = 10
      this.loadData()
    },
    loadData() {
        this.tableLoading = true
      this.queryParams.pageNum = this.tablePage.currentPage
      this.queryParams.pageSize = this.tablePage.pageSize
      getAction('/system/notice/list/self', this.queryParams).then((res) => {
        this.tablePage.totalResult = res.total
        this.tableData = res.rows
      }).finally(()=>{
        this.tableLoading = false
      })
    },
    showInfo(row) {
        this.$alert(row.noticeContent, row.noticeTitle, {
            confirmButtonText: this.language==='zh'?'OK':'OK',
            callback: action => {
                if(!row.isRead){
                    putAction('/system/notice/updateSend',{isRead: 1,noticeId:row.noticeId}).then(res=>{
                        this.loadData()
                    })
                }
            }
        });
    },
    pageChangeEvent({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.loadData()
    },
    show() {
      this.tablePage.currentPage = 1
      this.tablePage.pageSize = 10
      this.loadData()
      this.visible = true
    },
    cancel() {
        this.$emit('cancel')
      this.visible = false
    },
  }
}
</script>
<style lang="scss" scoped>
</style>
