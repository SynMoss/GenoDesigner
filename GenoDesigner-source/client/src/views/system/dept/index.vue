<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
    >
      <el-form-item label="DeptName" prop="deptName">
        <el-input
          v-model="queryParams.deptName"
          placeholder="Please Enter DeptName"
          clearable
          @keyup.enter.native="handleQuery"
          @clear="handleQuery"
          @blur="queryParams.deptName= $event.target.value.trim()"
        />
      </el-form-item>
      <el-form-item label="Status" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="DeptStatus"
          clearable
          @keyup.enter.native="handleQuery"
          @clear="handleQuery"
        >
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery" style="font-size: 14px;padding: 10px 20px;">Search</el-button>
        <el-button @click="resetQuery" style="font-size: 14px;padding: 10px 20px;">Reset</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
        style="font-size: 14px;padding: 10px 20px;"
          type="primary"
          @click="handleAdd"
          v-hasPermi="['system:dept:add']"
          >New</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
        style="font-size: 14px;padding: 10px 20px;"
          @click="toggleExpandAll"
          >Unfold/Fold
        </el-button>
      </el-col>
    </el-row>
    <div class="table-f">
      <vxe-table

        resizable
        border="inner"
        max-height="100%"
        v-loading="loading"
        ref="SysDeptList"
        row-id="deptId"
        :tree-config="{
          transform: false,
          rowField: 'deptId',
          parentField: 'parentId',
        }"
        :data="deptList"
        @toggle-tree-expand="toggleExpandChangeEvent"
      >
        <vxe-column field="deptName" title="DeptName" tree-node min-width="160px"></vxe-column>
        <vxe-column field="orderNum" title="OrderNum"></vxe-column>
        <vxe-column field="status" title="Status">
          <template slot-scope="scope">
            <dict-tag
              :options="dict.type.sys_normal_disable"
              :value="scope.row.status"
            />
          </template>
        </vxe-column>
        <vxe-column field="createTime" title="CreateTime" min-width="150px"></vxe-column>
        <vxe-column field="j" title="Operate" min-width="90px">
          <template slot-scope="scope">
            <a
              @click="handleUpdate(scope.row)"
              type="text"
              size="small"
              class="table-opera"
              v-hasPermi="['system:user:edit']"
              >Edit</a
            >
            <a
              @click="handleAdd(scope.row)"
              type="text"
              size="small"
              class="table-opera"
              v-hasPermi="['system:menu:add']"
              >New</a
            >
            <a
              @click="handleDelete(scope.row)"
              type="text"
              size="small"
              class="table-opera"
              v-hasPermi="['system:user:remove']"
              >Delete</a
            >
          </template>
        </vxe-column>
      </vxe-table>
    </div>

         <el-dialog
      :close-on-click-modal="false"
      :title="title"
      :visible.sync="open"
      width="600px"
      append-to-body
    >
      <el-form
        label-position="top"
        size="small"
        v-loading="getLoading"
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
      >
        <el-row>
          <el-col :span="24" v-if="form.parentId !== '0'">
            <el-form-item label="ParentID" prop="parentId">
              <treeselect
                v-model="form.parentId"
                :options="deptOptions"
                :normalizer="normalizer"
                placeholder="Choose ParentID"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="DeptName" prop="deptName">
              <el-input v-model="form.deptName" placeholder="Please Enter DeptName" @blur="form.deptName = $event.target.value.trim()"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Display OrderNum" prop="orderNum">
              <el-input-number
                v-model="form.orderNum"
                controls-position="right"
                :min="0"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Leader" prop="leader">
              <el-input
                v-model="form.leader"
                @blur="form.leader = $event.target.value.trim()"
                placeholder="Please Enter Leader"
                maxlength="20"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Phone" prop="phone">
              <el-input
                v-model="form.phone"
                @blur="form.phone = $event.target.value.trim()"
                placeholder="Please Enter Phone"
                maxlength="11"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Email" prop="email">
              <el-input
                v-model="form.email"
                @blur="form.email = $event.target.value.trim()"
                placeholder="Please Enter Email"
                maxlength="50"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="DeptStatus">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                  >{{ dict.label }}</el-radio
                >
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button
          type="primary"
          @click="submitForm"
          :disabled="subLoading"
          :loading="subLoading"
          >OK</el-button
        >
        <el-button @click="cancel" :disabled="subLoading" :loading="subLoading"
          >Cancel</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listDept,
  getDept,
  delDept,
  addDept,
  updateDept,
  listDeptExcludeChild,
} from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Dept",
  dicts: ["sys_normal_disable"],
  components: { Treeselect },
  data() {
    return {
      getLoading: false,

      loading: true,

      showSearch: true,

      deptList: [],

      deptOptions: [],

      title: "",

      open: false,

      isExpandAll: false,

      refreshTable: true,

      queryParams: {
        deptName: '',
        status: undefined,
      },
      subLoading: false,

      form: { dictName:'', dictType:'', remark:''},

      rules: {
        parentId: [
          { required: true, message: "ParentID Cannot Be Empty", trigger: "blur" },
        ],
        deptName: [
          { required: true, message: "DeptName Cannot Be Empty", trigger: "blur" },
          { max: 30, message: 'DeptName Cannot Exceed 30 Characters', trigger: 'blur' }
        ],
        orderNum: [
          { required: true, message: "OrderNum Cannot Be Empty", trigger: ["blur", "change"] },
        ],
        email: [
          {
            type: "email",
            message: "Please Enter A Valid Email Address",
            trigger: ["blur", "change"],
          },
        ],
        phone: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "Please Enter A Valid Phone",
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {

    getList() {
      this.loading = true;
      listDept(this.queryParams).then((response) => {
        this.deptList = this.handleTree(response.data, "deptId");
        this.loading = false;
      });
    },

    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children,
      };
    },

    cancel() {
      this.open = false;
      this.reset();
    },

    reset() {
      this.form = {
        deptId: undefined,
        parentId: undefined,
        deptName: undefined,
        orderNum: undefined,
        leader: undefined,
        phone: undefined,
        email: undefined,
        status: "0",
      };
      this.resetForm("form");
    },

    handleQuery() {
      this.queryParams.deptName = this.queryParams.deptName.trim()
      this.getList();
    },

    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    handleAdd(row) {
      this.reset();
      if (row != undefined) {
        this.form.parentId = row.deptId;
      }
      this.open = true;
      this.title = "Add Dept";
      listDept().then((response) => {
        this.deptOptions = this.handleTree(response.data, "deptId");
      });
    },

    toggleExpandAll() {
      if(!this.isExpandAll){
        this.$refs.SysDeptList.setAllTreeExpand(true)
      }else{
        this.$refs.SysDeptList.clearTreeExpand()
      }
      this.isExpandAll = !this.isExpandAll;
    },

    handleUpdate(row) {
      this.reset();
      this.getLoading = true;
      this.open = true;
      getDept(row.deptId)
        .then((response) => {
          this.form = response.data;
          this.title = "Edit Dept";
        })
        .finally(() => {
          this.getLoading = false;
        });
      listDeptExcludeChild(row.deptId).then((response) => {
        this.deptOptions = this.handleTree(response.data, "deptId");
      });
    },

    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.subLoading = true;
          if (this.form.deptId != undefined) {
            updateDept(this.form)
              .then((response) => {
                this.$modal.msgSuccess("Edit Success");
                this.open = false;
                this.getList();
              })
              .finally(() => {
                this.subLoading = false;
              });
          } else {
            addDept(this.form)
              .then((response) => {
                this.$modal.msgSuccess("Add Success");
                this.open = false;
                this.getList();
              })
              .finally(() => {
                this.subLoading = false;
              });
          }
        }
      });
    },

    handleDelete(row) {
      this.$modal
        .confirm('Check Whether To Confirm That The Deletion Name Is Set To"' + row.deptName + '"Data Items？')
        .then(function () {
          return delDept(row.deptId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("Delete Success");
        })
        .catch(() => {});
    },
    toggleExpandChangeEvent({ row, expanded }) {
      const $table = this.$refs.SysDeptList;
      console.log(
        "Node Expansion Event",
        expanded,
        "Get The Parent Node：",
        $table.getParentRow(row)
      )
    },
    getTreeExpansionEvent() {
      const $table = this.$refs.SysDeptList;
      const treeExpandRecords = $table.getTreeExpandRecords();
      VXETable.modal.alert(treeExpandRecords.length);
    },
  },
};
</script>
<style scoped>
.table-f {
  height: calc(100vh - 230px);
}
</style>
>
