<template>
  <div>
    <div class="top-top">
      <div>
        <span>{{language==='zh'?zh_list.a:'Part'}} ID：</span>
        <el-input
          size="small"
          :placeholder="language==='zh'?zh_list.d:'Please Type in an Part ID'"
          v-model="input2"
          maxlength="100"
          class="input-default"
          @keyup.enter.native="queryProgramme"
          clearable
          @clear="queryProgramme"
        >
        </el-input>
      </div>
      <div>
        <span>{{language==='zh'?zh_list.L:'Name'}}：</span>
        <el-input
          size="small"
          :placeholder="language==='zh'?zh_list.e:'Please Type in a Name'"
          v-model="input"
          maxlength="100"
          class="input-default"
          @keyup.enter.native="queryProgramme"
          clearable
        >
        </el-input>
      </div>
      <el-button type="primary" @click="queryProgramme">{{language==='zh'?zh_list.f:'Search'}}</el-button>
      <el-button @click="reset">{{language==='zh'?zh_list.g:'Resetting'}}</el-button>
    </div>
    <div class="app-container">
      <div class="design-list-top">
        <div>{{language==='zh'?zh_list.h:'List'}}</div>
        <div class="design-list-top-right">
          <el-button
            type="primary"
            @click="$refs.partEditDialog.add()"
            v-hasPermi="['part:part:add']"
          >
            {{language==='zh'?zh_list.Y:'New Parts'}}
          </el-button>
        </div>
      </div>
      <div class="table-f">
        <vxe-table
          :sort-config="{ remote: true }"
          @sort-change="sortChangeEvent"
          :filter-config="{ remote: true }"
          @filter-change="filterChangeEvent"
          border="inner"
          height="100%"
          class="table-self"
          header-cell-class-name="table-header-self"
          cell-class-name="table-cell-self"
          :align="allAlign"
          :data="tableData"
          :loading="tableLoading"
          :seq-config="{
            startIndex:
              (this.tablePage.currentPage - 1) * this.tablePage.pageSize,
          }"
          ref="projectTable"
        >
          <vxe-column type="seq" :title="language==='zh'?zh_list.i:'NO'" width="80px"></vxe-column>
          <vxe-column field="code" :title="language==='zh'?zh_list.j:'Part ID'"></vxe-column>
          <vxe-column field="name" :title="language==='zh'?zh_list.L:'Name'"></vxe-column>
          <vxe-column field="length" :title="language==='zh'?zh_list.K:'Size'" sortable></vxe-column>
          <vxe-column
            field="description"
            :title="language==='zh'?zh_list.l:'Description'"
            show-overflow="ellipsis"
          ></vxe-column>
          <vxe-column field="createTime" :title="language==='zh'?zh_list.m:'Time'" sortable></vxe-column>
          <vxe-column field="j" :title="language==='zh'?zh_list.n:'Operation'">
            <template #default="{ row }">
              <a
                @click="view(row)"
                type="text"
                size="small"
                class="table-opera"
                v-hasPermi="['part:part:detail']"
              >
              {{language==='zh'?zh_list.o:'Details'}}
              </a>
              <a
                @click="toPreview(row)"
                type="text"
                size="small"
                class="table-opera"
                v-hasPermi="['part:part:preview']"
              >
              {{language==='zh'?zh_list.p:'Preview'}}
              </a>
              <a
                @click="$refs.partEditDialog.edit(row)"
                type="text"
                size="small"
                class="table-opera"
                v-hasPermi="['part:part:edit']"
              >
                {{language==='zh'?zh_list.q:'Edit'}}
              </a>
              <a
                @click="confirmRemove(row)"
                type="text"
                size="small"
                class="table-opera"
                v-hasPermi="['part:part:delete']"
              >
                {{language==='zh'?zh_list.r:'Delete'}}
              </a>
            </template>
          </vxe-column>
          <template #empty>
            <el-empty :description="language==='zh'?zh_list.C:'No Results'"></el-empty>
          </template>
        </vxe-table>
      </div>
      <div class="pager-flex">
        <div class="pager-flex-total" v-text="totalShow()"></div>
        <vxe-pager
          class-name="pager-self"
          size="small"
          border
          :loading="tableLoading"
          :current-page="tablePage.currentPage"
          :page-size="tablePage.pageSize"
          :total="tablePage.totalResult"
          :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'Sizes', 'FullJump']"
          @page-change="pageChangeEvent"
        ></vxe-pager>
      </div>
      <part-edit-dialog ref="partEditDialog" @ok="reset"></part-edit-dialog>
      <part-info-dialog ref="partInfoDialog"></part-info-dialog>
      <preview-dialog ref="previewDialog"></preview-dialog>
      <el-dialog
        :close-on-click-modal="true"
        :title="language==='zh'?zh_list.s:'Confirmation'"
        :visible.sync="confirmRemoveVisible"
        width="520px"
        append-to-body
      >
        <div class="diaglog-remove-icon"><i class="el-icon-warning"></i></div>
        <div class="dialog-p">
          <template v-if="language==='zh'">
            {{this.zh_list.t}}<span style="color: red">{{ data.name }}</span>{{this.zh_list.u}}
          </template>
          <template v-else>
            Do you agree to delete "<span style="color: red">{{ data.name }}</span>"
          </template>
        </div>
        <div class="dialog-p">{{language==='zh'?zh_list.v:'Cannot Recover after Deletion!'}}</div>
        <div slot="footer" class="dialog-footer">
          <el-button
            @click="confirmRemoveVisible = false"
            :disabled="subLoading"
            :loading="subLoading"
          >
            {{language==='zh'?zh_list.R:'Cancel'}}
          </el-button>
          <el-button
            type="primary"
            @click="remove(data)"
            :disabled="subLoading"
            :loading="subLoading"
          >
            {{language==='zh'?zh_list.S:'OK'}}
          </el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import { listPart, deletePart } from "@/api/system/part";
import PartEditDialog from "./modules/PartEditDialog";
import PartInfoDialog from "./modules/PartInfoDialog";
import PreviewDialog from "./modules/PreviewDialog";
import { zh_list } from "@/data/constant";
export default {
  name: "PartList",
  components: {
    PartEditDialog,
    PartInfoDialog,
    PreviewDialog,
  },
  data() {
    return {
      zh_list,
      language: process.env.VUE_APP_LANGUAGE,
      subLoading: false,
      allAlign: null,
      tableLoading: false,
      tablePage: {
        currentPage: 1,
        pageSize: 10,
        totalResult: 100,
      },
      data: {},
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      confirmRemoveVisible: false,
      tableData: [],
      input: "",
      input2: "",
    };
  },
  created() {
        this.loadData();
  },
                              methods: {
        reset() {
      this.input = "";
      this.input2 = "";
      this.tablePage.currentPage = 1;
      this.tablePage.pageSize = 10;
      this.queryParams = {};
      this.loadData();
    },
        loadData() {
      this.queryParams.pageNum = this.tablePage.currentPage;
      this.queryParams.pageSize = this.tablePage.pageSize;
      this.tableLoading = true;
      listPart(this.queryParams)
        .then((res) => {
          this.tablePage.totalResult = res.total;
          this.tableData = res.rows;
        })
        .finally(() => {
          this.tableLoading = false;
        });
    },
        pageChangeEvent({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.loadData();
    },
        sortChangeEvent({ property, order }) {
      console.log(property);
      console.log(order);
      this.tablePage.currentPage = 1;
      this.queryParams.orderByColumn = property;
      this.queryParams.isAsc = order;
      this.loadData();
    },
        filterChangeEvent({ property, values }) {
      console.log(property);
      console.log(values);
      this.queryParams.createStaffNoList = values;
      this.loadData();
    },
        confirmRemove(row) {
      this.data = row;
      this.confirmRemoveVisible = true;
    },
        remove(row) {
      this.subLoading = true;
      deletePart({ id: row.id })
        .then((res) => {
          console.log(res.msg);
          this.confirmRemoveVisible = false;
          this.loadData();
          this.$modal.msgSuccess(this.language==='zh'?this.zh_list.w:"Deletion successful");
        })
        .finally(() => {
          setTimeout(() => {
            this.subLoading = false;
          }, 500);
        });
    },
        view(row) {
      this.$refs.partInfoDialog.openDialog(row);
    },
        handleCommand(command, row) {
      console.log(command);
      if (command === "lDialog") {
        this.$refs.log.show(row);
      } else if (command === "mDialog") {
        this.$refs.memberDialog.list(row);
        this.$refs.memberDialog.getSelectOptions(row);
      }
    },
        queryProgramme() {
      this.queryParams.name = this.input;
      this.queryParams.code = this.input2;
      this.loadData();
    },
        totalShow() {
      let total = this.tablePage.totalResult;
      return this.language==='zh'?this.zh_list.x+total+this.zh_list.nape:total+" items in total";
    },
                                        toPreview(row) {
      this.$refs.previewDialog.show(row);
    },
  },
};
</script>
<style></style>
<style lang="scss" scoped>
.list-top {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  .list-top-right {
    display: flex;
    div {
      margin: 0px 20px;
    }
  }
}
.table-f {
  height: calc(100vh - 300px);
}
.top-top {
  border-top: 1px solid #e8e9ed;
  padding: 10px 20px;
  font-size: 14px;
  display: flex;
  align-items: center;
  background-color: #fff;
  div {
    margin-right: 10px;
  }
}
.design-list-top {
  margin-bottom: 20px;
  display: flex;
  text-align: left;
  justify-content: space-between;
}
.input-default {
  width: 300px;
}
</style>
