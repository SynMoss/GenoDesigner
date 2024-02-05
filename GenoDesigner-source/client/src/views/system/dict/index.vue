<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" >
      <el-form-item label="DictName" prop="dictName">
        <el-input
          v-model="queryParams.dictName"
          placeholder="Please Enter DictName"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
          @blur="queryParams.dictName = $event.target.value.trim()"
          @clear="handleQuery"
        />
      </el-form-item>
      <el-form-item label="DictType" prop="dictType">
        <el-input
          v-model="queryParams.dictType"
          placeholder="Please Enter DictType"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
          @blur="queryParams.dictType = $event.target.value.trim()"
          @clear="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Status" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="Dict Status"
          clearable
          style="width: 240px"
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
      <el-form-item label="CreateTime">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="StartDate"
          end-placeholder="EndDate"
          clearable
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="font-size: 14px;padding: 10px 20px;" @click="handleQuery">Search</el-button>
        <el-button @click="resetQuery" style="font-size: 14px;padding: 10px 20px;">Reset</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          @click="handleAdd"
          v-hasPermi="['system:dict:add']"
        >New</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:dict:remove']"
        >Delete</el-button>
      </el-col>
             <el-col :span="1.5">
        <el-button
          @click="handleRefreshCache"
          v-hasPermi="['system:dict:remove']"
        >Refresh The Cache</el-button>
      </el-col>
    </el-row>
    <div class="table-f">
    <vxe-table
      ref="SysDIctList"
      :data="typeList"
      height="100%"
      border="inner"
      v-loading="loading"
      :row-config="{isHover: true}"
      @checkbox-all="selectChangeEvent"
      show-overflow
      @checkbox-change="selectChangeEvent"
      :seq-config="{startIndex:(this.tablePage.currentPage-1) * this.tablePage.pageSize}">
      <vxe-column type="checkbox" min-width="50px" fixed="left"></vxe-column>
      <vxe-column type="seq" title="Seq" min-width="50px" fixed="left" show-overflow="tooltip"></vxe-column>
      <vxe-column field="dictName" title="DictName" min-width="100px" fixed="left" show-overflow="tooltip"></vxe-column>
      <vxe-column  title="DictType" min-width="100px" show-overflow="tooltip">
        <template slot-scope="scope">
          <router-link :to="'/system/dict-data/index/' + scope.row.dictId" class="link-type">
            <span>{{ scope.row.dictType }}</span>
          </router-link>
        </template>
      </vxe-column>
      <vxe-column field="status" title="Status" min-width="100px" show-overflow="tooltip">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </vxe-column>
      <vxe-column field="remark" title="Remark" min-width="100px" show-overflow="tooltip"></vxe-column>
      <vxe-column field="createTime" title="CreateTime" min-width="100px" show-overflow="tooltip"></vxe-column>
      <vxe-column field="j" title="Operate" min-width="150px" fixed="right">
      <template slot-scope="scope">
        <a @click="handleUpdate(scope.row)" type="text" size="small" class="table-opera" v-hasPermi="['system:user:edit']"
          >Edit</a
        >

        <a @click="handleDelete1(scope.row)" type="text" size="small" class="table-opera" v-hasPermi="['system:user:remove']"
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
        background
        size="small"
        :loading="loading"
        :current-page="tablePage.currentPage"
        :page-size="tablePage.pageSize"
        :total="tablePage.totalResult"
        :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'Sizes', 'FullJump']"
        @page-change="pageChangeEvent">
      </vxe-pager>
    </div>

         <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form label-position="top" size="small" ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="DictName" prop="dictName">
          <el-input v-model="form.dictName" placeholder="Please Enter DictName" @blur="form.dictName = $event.target.value.trim()"/>
        </el-form-item>
        <el-form-item label="DictType" prop="dictType">
          <el-input v-model="form.dictType" placeholder="please Enter DictType" @blur="form.dictType = $event.target.value.trim()"/>
        </el-form-item>
        <el-form-item label="Status" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Remark" prop="remark">
          <el-input
          v-model="form.remark"
          type="textarea"
          placeholder="Please Enter Content"
          @blur="form.remark = $event.target.value.trim()"
          resize="none"
          maxlength="256"
          show-word-limit
          :rows="6"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">OK</el-button>
        <el-button @click="cancel">Cancel</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listType, getType, delType, addType, updateType, refreshCache } from "@/api/system/dict/type";

export default {
  name: "Dict",
  dicts: ['sys_normal_disable'],
  data() {
    return {

      loading: true,

      ids: [],

      single: true,

      multiple: true,

      showSearch: true,

      total: 0,

      typeList: [],

      title: "",

      open: false,

      dateRange: [],

      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dictName: '',
        dictType: '',
        status: undefined
      },

      form: { dictName:'', dictType:'', remark:''},

      rules: {
        dictName: [
          { required: true, message: "DictName Cannot Be Empty", trigger: "blur" }
        ],
        dictType: [
          { required: true, message: "DictType Cannot Be Empty", trigger: "blur" }
        ]
      },
      tablePage: {
        currentPage: 1,
        pageSize: 10,
        totalResult: 100
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    getList() {
      this.queryParams.pageNum = this.tablePage.currentPage
      this.queryParams.pageSize = this.tablePage.pageSize
      this.loading = true;
      listType(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.typeList = response.rows;
          this.tablePage.totalResult = response.total
          this.loading = false;
        }
      );
    },

    cancel() {
      this.open = false;
      this.reset();
    },

    reset() {
      this.form = {
        dictId: '',
        dictName: '',
        dictType: '',
        status: "0",
        remark: ''
      };
      this.resetForm("form");
    },

    handleQuery() {
      this.queryParams.dictName = this.queryParams.dictName.trim()
      this.queryParams.dictType = this.queryParams.dictType.trim()
      this.tablePage.currentPage = 1
      this.getList();
    },

    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },

    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "Add DictType";
    },

    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.dictId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },

    handleUpdate(row) {
      this.reset();
      const dictId = row.dictId || this.ids
      getType(dictId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "Edit DictType";
      });
    },

    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
           if (this.form.dictId != undefined&&this.form.dictId != null&&this.form.dictId != '') {
            updateType(this.form).then(response => {
              this.$modal.msgSuccess("Edit Success");
              this.open = false;
              this.getList();
            });
          } else {
            addType(this.form).then(response => {
              this.$modal.msgSuccess("Add Success");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    handleDelete(row) {
      let dictIds = this.ids
      let dnames=this.dictNames;
      this.$modal.confirm('Check Whether To Confirm That The Deletion DictName Is Set To"' + dnames + '"Data Items？').then(function() {
        return delType(dictIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Delete Success");
      }).catch(() => {});
    },
    handleDelete1(row) {
      let dictIds=row.dictId;
      this.$modal.confirm('Check Whether To Confirm That The Deletion DictName Is Set To"' + row.dictName + '"Data Items？').then(function() {
        return delType(dictIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Delete Success");
      }).catch(() => {});
    },

    handleExport() {
      this.queryParams.ids=this.ids,
      this.download('/system/dict/type/export', {
        ...this.queryParams
      }, `type_${new Date().getTime()}.xlsx`)
    },

    handleRefreshCache() {
      refreshCache().then(() => {
        this.$modal.msgSuccess("Refresh Success");
        this.$store.dispatch('dict/cleanDict');
      });
    },

    selectChangeEvent ({ checked }) {
      const records = this.$refs.SysDIctList.getCheckboxRecords()
      let arr=[]
      let arr1 = []
      for(let item of records){
        arr.push(item.dictId)
        arr1.push(item.dictName)
      }
      this.ids=arr
      this.dictNames=arr1

      if (records && records.length) {
          this.multiple = false;
      } else{
          this.multiple = true;
      }
    },
    getSelectEvent () {
      let selectRecords = this.$refs.SysDIctList.getCheckboxRecords()
      VXETable.modal.alert(selectRecords.length)
    },

    pageChangeEvent({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.getList()
    },

    totalShow() {
      let total = this.tablePage.totalResult;
      let str = "Total ";
      let query = this.queryParams;
      if (
        query.projectName ||
        (query.createStaffNoList && query.createStaffNoList.length != 0)
      ) {
        str = "Searched ";
      }
      return str + total + " Items";
    },
    dataChange() {
      if(this.dateRange == null){
      this.getList();
      }
    },
  }
};
</script>
<style lang="scss" scoped>
  .table-f{
    height: calc(90vh - 250px);
  }
</style>
