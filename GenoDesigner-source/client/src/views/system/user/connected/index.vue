<template>
  <div>
    <div class="app-container">
      <div class="design-list-top">
        <div>
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button type="primary" @click="newExternalPaccout"
                >New</el-button
              >
            </el-col>
          </el-row>
        </div>
        <div class="design-list-top-right">
          <el-input
            size="small"
            placeholder="Please Enter A System Name"
            clearable
            v-model="input"
            maxlength="20"
            @blur="input = $event.target.value.trim()"
            @change="handleQuery"
            class="input-default"
          >
            <template v-slot:suffix @click="queryExternalPaccoutByName">
              <i class="el-input__icon el-icon-search"></i>
            </template>
          </el-input>
        </div>
      </div>
      <div class="table-self">
        <vxe-table
          :sort-config="{ remote: true }"
          @sort-change="sortChangeEvent"
          :filter-config="{ remote: true }"
          border="inner"
          height="100%"
          header-cell-class-name="table-header-self"
          cell-class-name="table-cell-self"
          :align="allAlign"
          :data="tableData"
          :loading="tableLoading"
          :seq-config="{
            startIndex:
              (this.tablePage.currentPage - 1) * this.tablePage.pageSize,
          }"
          ref="connectedAccount"
        >
          <vxe-column type="seq" title="Seq" min-width="50px"></vxe-column>
          <vxe-column
            field="externalSystemName"
            title="External System Name"
            show-overflow="tooltip"
            min-width="100px"
          ></vxe-column>
          <vxe-column
            field="externalSystemUserName"
            title="External System Username"
            show-overflow="tooltip"
            min-width="100px"
          ></vxe-column>
          <vxe-column
            field="bindTime"
            title="Bind Time"
            min-width="200px"
            sortable
          >
            <template #default="{ row }">
              <div v-if="row.bindTime">{{ row.bindTime }}</div>
              <div v-else class="default-else-el">Unbound</div>
            </template>
          </vxe-column>
          <vxe-column field="j" title="Operation" fixed="right" width="150px">
            <template #default="{ row }">
              <a
                @click="confirmUnBind(row)"
                type="text"
                size="small"
                class="table-opera"
                v-if="row.isBind == 1"
                >Unbind</a
              >
              <a
                @click="confirmBind(row)"
                type="text"
                size="small"
                class="table-opera"
                v-if="row.isBind == 0"
                >Bind</a
              >
              <a
                @click="editExternalPaccout(row)"
                type="text"
                size="small"
                class="table-opera"
                v-if="row.isBind == 0"
                >Edit</a
              >
              <a
                @click="confirmRemove(row)"
                type="text"
                size="small"
                class="table-opera"
                v-if="row.isBind == 0"
                >Delete</a
              >
            </template>
          </vxe-column>
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
        >
        </vxe-pager>
      </div>
      <external-paccout-dialog
        ref="ExternalPaccoutDialog"
        @ok="reset"
      ></external-paccout-dialog>
      <el-dialog
        :close-on-click-modal="true"
        title="Confirm Deletion"
        :visible.sync="confirmRemoveVisible"
        width="520px"
        append-to-body
      >
        <div class="diaglog-remove-icon"><i class="el-icon-warning"></i></div>
        <div class="dialog-p">
          {{
            "Confirm deleting associated account for system  " + data.externalSystemName + " ?"
          }}
        </div>
        <div class="dialog-p">Irreversible deletion!</div>
        <div slot="footer" class="dialog-footer">
          <el-button
            @click="confirmRemoveVisible = false"
            :disabled="subLoading"
            :loading="subLoading"
            >Cancel</el-button
          >
          <el-button
            type="primary"
            @click="remove()"
            :disabled="subLoading"
            :loading="subLoading"
            >Confirm</el-button
          >
        </div>
      </el-dialog>
      <el-dialog
        :close-on-click-modal="true"
        title="Unbind confirmation"
        :visible.sync="confirmUnBindVisible"
        width="520px"
        append-to-body
      >
        <div class="diaglog-remove-icon"><i class="el-icon-warning"></i></div>
        <div class="dialog-p">
          Unlinking the system account will permanently remove its associated information and permissions.
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button
            @click="confirmUnBindVisible = false"
            :disabled="subLoading"
            :loading="subLoading"
            >Cancel</el-button
          >
          <el-button
            type="primary"
            @click="unBind()"
            :disabled="subLoading"
            :loading="subLoading"
            >Confirm</el-button
          >
        </div>
      </el-dialog>
      <el-dialog
        :close-on-click-modal="true"
        title="Bind confirmation"
        :visible.sync="confirmBindVisible"
        width="520px"
        append-to-body
      >
        <div class="diaglog-remove-icon"><i class="el-icon-warning"></i></div>
        <div class="dialog-p">Confirm binding the system?</div>
        <div slot="footer" class="dialog-footer">
          <el-button
            @click="confirmBindVisible = false"
            :disabled="subLoading"
            :loading="subLoading"
            >Cancel</el-button
          >
          <el-button
            type="primary"
            @click="bind()"
            :disabled="subLoading"
            :loading="subLoading"
            >Confirm</el-button
          >
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import { table } from "@/mixins/table";
import externalPaccoutDialog from "./modules/ExternalPaccoutDialog";
import {
  listExternalPaccout,
  bindExternalPaccout,
  unBindExternalPaccout,
  removeExternalPaccout,
} from "@/api/system/externalpaccout";
export default {
  name: "ConnectedAccount",
  mixins: [table],
  components: { externalPaccoutDialog },
  data() {
    return {
      subLoading: false,
      allAlign: null,
      tableLoading: false,
      tablePage: {
        currentPage: 1,
        pageSize: 10,
        totalResult: 100,
      },
      tableData: [],
      checkedList: [],
      disableHandle: true,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      input: "",
      data: {},
      confirmRemoveVisible: false,
      confirmUnBindVisible: false,
      confirmBindVisible: false,
      columnSelected: [],
    };
  },
  created() {
    this.loadData();
  },
  mounted() {},
  methods: {
    reset() {
      this.tablePage.currentPage = 1;
      this.tablePage.pageSize = 10;
      this.updateDateRange = [];
      this.input = "";
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
      };
      this.loadData();
    },
    loadData() {
      this.queryParams.pageNum = this.tablePage.currentPage;
      this.queryParams.pageSize = this.tablePage.pageSize;
      this.tableLoading = true;
      listExternalPaccout(this.queryParams)
        .then((res) => {
          this.tablePage.totalResult = res.total;
          this.tableData = res.rows;
        })
        .finally(() => {
          this.tableLoading = false;
        });
    },
    handleQuery() {
      this.input = this.input.trim();
      this.queryParams.externalSystemName = this.input;
      this.tablePage.currentPage = 1
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
    refreshTable() {
      this.$refs.connectedAccount.refreshColumn();
    },
    confirmRemove(row) {
      this.data = row;
      this.confirmRemoveVisible = true;
    },
    remove() {
      this.subLoading = true
      removeExternalPaccout(this.data.id).then((res) => {
        this.confirmRemoveVisible = false;
        setTimeout(()=>{
          this.subLoading = false
        },500)
        this.loadData();
        this.$modal.msgSuccess("Deletion successful.");
      });
    },
    confirmUnBind(row) {
      this.data = row;
      this.confirmUnBindVisible = true;
    },
    unBind() {
      this.subLoading = true
      unBindExternalPaccout(this.data.id).then((res) => {
        this.confirmUnBindVisible = false;
        setTimeout(()=>{
          this.subLoading = false
        },500)
        this.loadData();
        this.$modal.msgSuccess("UnBinding successful.");
      });
    },
    confirmBind(row) {
      this.data = row;
      this.confirmBindVisible = true;
    },
    bind() {
      this.subLoading = true
      bindExternalPaccout(this.data.id).then((res) => {
        this.confirmBindVisible = false;
        setTimeout(()=>{
          this.subLoading = false
        },500)
          this.loadData();
        this.$modal.msgSuccess("Binding successful.");
      });
    },
    newExternalPaccout() {
      this.$refs.ExternalPaccoutDialog.add(this.tableData);
    },
    editExternalPaccout(row) {
      this.$refs.ExternalPaccoutDialog.edit(row);
    },
    totalShow() {
      const total = this.tablePage.totalResult;
      let str = "Total ";
      const query = this.queryParams;
      if (
        query.designName ||
        (query.designType && query.designType.length != 0) ||
        (query.state && query.state.length != 0)
      ) {
        str = "Found ";
      }
      return str + total + " items";
    },
    pageChangeEvent({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.loadData();
    },
  },
};
</script>
<style></style>
<style lang="scss" scoped>
.design-list-top {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  .design-list-top-right {
    flex: 0 0 340px;
    margin-left: auto;
  }
}
.input-default {
  width: 300px;
}
.default-else-el {
  color: #cccccc;
  margin-right: 10px;
  cursor: auto;
  text-decoration: none;
}
.setting-class {
  font-size: 20px;
  text-decoration: none;
  color: black;
  margin-left: 10px;
  margin-top: 5px;
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
.default-else-el {
  color: #cccccc;
  margin-right: 10px;
  cursor: auto;
  text-decoration: none;
}
.table-self {
  //height: 530px;
  height: calc(100vh - 250px);
}
</style>
