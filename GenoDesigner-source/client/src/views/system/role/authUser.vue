<template>
  <div class="app-container">
     <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="UserName" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="Please Enter UserName"
          clearable
          style="width: 240px"
          @blur="queryParams.userName = $event.target.value.trim()"
          @clear="handleQuery"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="PhoneNumber" prop="phonenumber">
        <el-input
          v-model="queryParams.phonenumber"
          placeholder="Please Enter PhoneNumber"
          clearable
          style="width: 240px"
          @blur="queryParams.phonenumber = $event.target.value.trim()"
          @clear="handleQuery"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="handleQuery">Search</el-button>
        <el-button size="mini" @click="resetQuery">Reset</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          @click="openSelectUser"
          v-hasPermi="['system:user:edit']"
        >Add User</el-button>
      </el-col>
           </el-row>
    <div class="table-f">
    <vxe-table
    border="inner"
    height="100%"
      ref="xTable1"
      :data="userList"
      auto-resize
      v-loading="loading"
      :row-config="{isHover: true}"
      @checkbox-all="selectChangeEvent"
      show-overflow

      @checkbox-change="selectChangeEvent"
      :seq-config="{startIndex:(this.tablePage.currentPage-1) * this.tablePage.pageSize}">
      <vxe-column type="checkbox" width="7%"></vxe-column>
      <vxe-column field="userName" title="UserName" show-overflow="tooltip" min-width="100px"></vxe-column>
      <vxe-column field="nickName" title="NickName" show-overflow="tooltip" min-width="100px"></vxe-column>
      <vxe-column field="email" title="Email" show-overflow="tooltip" min-width="50px"></vxe-column>
      <vxe-column field="phonenumber" title="PhoneNumber" show-overflow="tooltip" min-width="50px"></vxe-column>
      <vxe-column field="status" title="Status"  min-width="50px">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </vxe-column>
      <vxe-column field="createTime" title="CreateTime" show-overflow="tooltip" min-width="150px"></vxe-column>
           </vxe-table>
  </div>
    <div class="pager-flex">
      <div class="pager-flex-total"></div>
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

    <select-user ref="select" :roleId="queryParams.roleId" @ok="handleQuery" />

    <div style="display:flex;justify-content: center;margin-left: -100px;">
      <el-form >
      <el-form-item>
        <el-button @click="handleClose"
          >Return</el-button>
      </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { allocatedUserList, authUserCancel, authUserCancelAll } from "@/api/system/role";
import selectUser from "./selectUser";

export default {
  name: "AuthUser",
  dicts: ['sys_normal_disable'],
  components: { selectUser },
  data() {
    return {
  
      loading: true,
      
      userIds: [],
    
      multiple: true,
      
      showSearch: true,
     
      total: 0,
      
      userList: [],
      
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleId: '',
        userName: '',
        phonenumber: ''
      },
      tablePage: {
        currentPage: 1,
        pageSize: 10,
        totalResult: 100
      },
      subLoading: false,
      roleName: ''
    };
  },
  created() {
    this.roleName = this.$route.query.roleName
    const roleId = this.$route.params && this.$route.params.roleId;
    if (roleId) {
      this.queryParams.roleId = roleId;
      this.getList();
    }
  },
  methods: {
    
    getList() {
      this.queryParams.pageNum = this.tablePage.currentPage
      this.queryParams.pageSize = this.tablePage.pageSize
      this.loading = true;
      allocatedUserList(this.queryParams).then(response => {
          this.userList = response.rows;
          this.tablePage.totalResult = response.total
          this.loading = false;
        }
      );
    },
  
    handleClose() {
      const obj = { path: "/system/role" };
      this.$tab.closeOpenPage(obj);
    },
   
    handleQuery() {
      this.queryParams.userName = this.queryParams.userName.trim()
      this.queryParams.phonenumber = this.queryParams.phonenumber.trim()
      this.tablePage.currentPage = 1
      this.getList();
    },
    
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
   
    openSelectUser() {
      this.$refs.select.show();
    },
    
    cancelAuthUser(row) {
      const roleId = this.queryParams.roleId;
      this.$modal.confirm('Confirm That You Want To Cancel' + row.userName + 'Of"' + this.roleName + '"Is The Role Authorized?').then(function() {
        return authUserCancel({ userId: row.userId, roleId: roleId });
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("The Authorization Was Cancelled Successfully");
      }).catch(() => {});
    },
    
    cancelAuthUserAll(row) {
      const roleId = this.queryParams.roleId;
      const userIds = this.userIds.join(",");
      this.$modal.confirm('Do You Uncheck The User Authorization Data Item?').then(function() {
        return authUserCancelAll({ roleId: roleId, userIds: userIds });
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("The Authorization Was Cancelled Successfully");
      }).catch(() => {});
    },
    
    selectChangeEvent ({ checked }) {
      const records = this.$refs.xTable1.getCheckboxRecords()
      let arr=[]
      for(let item of records){
        arr.push(item.userId)
      }
      this.userIds=arr
      
      if (records && records.length) {
          this.multiple = false;
      } else{
          this.multiple = true;
      }
    },
    getSelectEvent () {
      let selectRecords = this.$refs.xTable1.getCheckboxRecords()
      VXETable.modal.alert(selectRecords.length)
    },
   
    pageChangeEvent({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.getList()
    },
  }
};
</script>
<style lang="scss" scoped>
.table-f{
  height: calc(95vh - 300px)
  }
</style>
