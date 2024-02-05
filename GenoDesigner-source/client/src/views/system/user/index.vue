<template>
  <div class="sys-user">
    <div class="sys-user-flex">
             <div class="sys-user-flex-left">
        <div class="head-container">
          <el-input
            v-model.trim="deptName"
            placeholder="Please enter the department name"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            highlight-current
            @node-click="handleNodeClick"
            class="showtree"
          />
        </div>
      </div>
      <div style="flex: 0 0 20px" class="resize"></div>
             <div class="sys-user-flex-right">
        <div class="search-form label14">
          <el-form
            :model="queryParams"
            ref="queryForm"
            size="small"
            :inline="true"
            v-show="showSearch"
            label-width="68px"
          >
            <el-form-item label="Username" prop="userName">
              <el-input
                v-model="queryParams.userName"
                placeholder="Please enter the username"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
                @blur="queryParams.userName = $event.target.value.trim()"
                @clear="handleQuery"
              />
            </el-form-item>
            <el-form-item label="Phone number" prop="phonenumber">
              <el-input
                v-model="queryParams.phonenumber"
                placeholder="Please enter the phone number"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
                @blur="queryParams.phonenumber = $event.target.value.trim()"
                @clear="handleQuery"
              />
            </el-form-item>
            <el-form-item label="Email" prop="email">
              <el-input
                v-model="queryParams.email"
                placeholder="Please enter the email"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
                @blur="queryParams.email = $event.target.value.trim()"
                @clear="handleQuery"
              />
            </el-form-item>
            <el-form-item label="Status" prop="status">
              <el-select
                v-model="queryParams.status"
                placeholder="User status"
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
            <el-form-item label="Create time">
              <el-date-picker
                clearable
                v-model="dateRange"
                style="width: 240px"
                value-format="yyyy-MM-dd"
                type="daterange"
                range-separator="-"
                start-placeholder="Start time"
                end-placeholder="End time"

              ></el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button
                type="primary"
                size="mini"
                style="font-size: 14px; padding: 10px 20px"
                @click="handleQuery"
                >Search</el-button
              >
              <el-button
                size="mini"
                style="font-size: 14px; padding: 10px 20px"
                @click="resetQuery"
                >Reset</el-button
              >
            </el-form-item>
          </el-form>
        </div>
        <div class="table-data">
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                style="font-size: 14px; padding: 10px 20px"
                type="primary"
                @click="handleAdd"
                v-hasPermi="['system:user:add']"
                >Add</el-button
              >
            </el-col>
            <el-col :span="1.5">
              <el-button
                style="font-size: 14px; padding: 10px 20px"
                type="danger"
                :disabled="multiple"
                @click="handleDelete"
                v-hasPermi="['system:user:remove']"
                >Remove</el-button
              >
            </el-col>
                         <right-toolbar
              :showSearch.sync="showSearch"
              @queryTable="getList"
              :columns="columns"
            ></right-toolbar>
          </el-row>
          <div class="table-f">
          <vxe-table
            ref="SysUserList"
            :data="userList"
            max-height="100%"
            v-loading="loading"
            class="table-self"
            border="inner"
            :align="allAlign"
            header-cell-class-name="table-header-self"
            cell-class-name="table-cell-self"
            :row-config="{isHover: true}"
            @checkbox-all="selectChangeEvent"
            show-overflow
            @checkbox-change="selectChangeEvent"
            :seq-config="{startIndex:(this.tablePage.currentPage-1) * this.tablePage.pageSize}">
            <vxe-column type="checkbox" min-width="50px" ></vxe-column>
            <vxe-column type="seq" title="Number" min-width="50px" show-overflow="tooltip"></vxe-column>
            <vxe-column field="userName" title="UserName" min-width="100px" show-overflow="tooltip"></vxe-column>
            <vxe-column field="nickName" title="NickName" min-width="100px" show-overflow="tooltip"></vxe-column>
            <vxe-column field="dept.deptName" title="Dept Name" min-width="50px" show-overflow="tooltip">
              <template slot-scope="scope">
                <span v-if="!scope.row.dept" style="color: #cccccc;">
                  <p>--</p>
                </span>
                <span v-else>
                  <p>{{ scope.row.dept.deptName }}</p>
                </span>
              </template>
            </vxe-column>
            <vxe-column field="phonenumber" title="Phone Number" min-width="100px" show-overflow="tooltip">
              <template slot-scope="scope">
                <span v-if="!scope.row.phonenumber" style="color: #cccccc;">
                  <p>--</p>
                </span>
                <span v-else>
                  <p>{{ scope.row.phonenumber }}</p>
                </span>
              </template>
            </vxe-column>
            <vxe-column field="email" title="Email" min-width="100px" show-overflow="tooltip">
              <template slot-scope="scope">
                <span v-if="!scope.row.email" style="color: #cccccc;">
                  <p>--</p>
                </span>
                <span v-else>
                  <p>{{ scope.row.email }}</p>
                </span>
              </template>
            </vxe-column>
            <vxe-column field="status" title="Status" min-width="80px">
              <template slot-scope="scope" v-if="scope.row.userId !== 1">
                  <vxe-switch
                    v-model="scope.row.status"
                    open-value="0"
                    close-value="1"
                    @change="handleStatusChange(scope.row)"
                  ></vxe-switch>
                </template>
              </vxe-column>
              <vxe-column
                field="createTime"
                title="Create Time"
                min-width="120px"
                show-overflow="tooltip"
              ></vxe-column>
              <vxe-column
                field="j"
                title="Operation"
                min-width="140px"
                show-overflow="tooltip"
                fixed="right"
              >
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
                    @click="handleDelete1(scope.row)"
                    type="text"
                    size="small"
                    class="table-opera"
                    v-hasPermi="['system:user:remove']"
                    >Remove</a
                  >
                  <el-dropdown
                    @command="(command) => handleCommand(command, scope.row)"
                    v-hasPermi="['system:user:resetPwd', 'system:user:edit']"
                  >
                    <span class="el-dropdown-link">
                      <i class="el-icon-more table-mouse-hover"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item
                        command="handleResetPwd"
                        v-hasPermi="['system:user:resetPwd']"
                        >Reset Password</el-dropdown-item
                      >
                      <el-dropdown-item
                        command="handleAuthRole"
                        v-hasPermi="['system:user:edit']"
                        >Assign roles</el-dropdown-item
                      >
                    </el-dropdown-menu>
                  </el-dropdown>
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
      </div>
    </div>

         <el-dialog
      :close-on-click-modal="false"
      :title="title"
      :visible.sync="open"
      width="940px"
      append-to-body
    >
      <el-form
        v-loading="getLoading"
        label-position="top"
        ref="form"
        :model="form"
        size="small"
        :rules="rules"
        label-width="80px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Nick name" prop="nickName">
              <el-input
                v-model="form.nickName"
                @blur="form.nickName = $event.target.value.trim()"
                placeholder="Please enter the user nickname"
                maxlength="30"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Department affiliation" prop="deptId">
              <treeselect
                v-model="form.deptId"
                :options="deptOptions"
                :show-count="true"
                placeholder="Please search or select the department affiliation"
                :normalizer="my_normalizer"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Phone number" prop="phonenumber">
              <el-input
                v-model="form.phonenumber"
                @blur="form.phonenumber = $event.target.value.trim()"
                placeholder="Please enter the phone number"
                maxlength="11"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Email" prop="email">
              <el-input
                v-model="form.email"
                @blur="form.email = $event.target.value.trim()"
                placeholder="Please enter the email address"
                maxlength="50"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item
              v-if="form.userId == undefined"
              label="username"
              prop="userName"
            >
              <el-input
                v-model="form.userName"
                @blur="form.userName = $event.target.value.trim()"
                placeholder="Please enter the username"
                maxlength="30"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              v-if="form.userId == undefined"
              label="Password"
              prop="password"
            >
              <el-input
                v-model="form.password"
                @blur="form.password = $event.target.value.trim()"
                placeholder="Please enter the password"
                type="password"
                maxlength="20"
                auto-complete="new-password"
                show-password
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Gender">
              <el-select v-model="form.sex" placeholder="Please select the gender">
                <el-option
                  v-for="dict in dict.type.sys_user_sex"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Status">
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
        <el-row :gutter="20">
                     <el-col :span="12">
            <el-form-item label="Role" prop="roleIds">
              <el-select
                v-model="form.roleIds[0]"
                placeholder="Please select the role"
              >
                <el-option
                  v-for="item in roleOptions"
                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleId"
                  :disabled="item.status == 1 || item.roleId == 1"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="Remark">
              <el-input
                v-model="form.remark"
                @blur="form.remark = $event.target.value.trim()"
                type="textarea"
                placeholder="Please enter the content"
                resize="none"
                maxlength="256"
                show-word-limit
                :rows="6"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel" :disabled="subLoading" :loading="subLoading"
          >Cancel</el-button
        >
        <el-button
          type="primary"
          @click="submitForm"
          :disabled="subLoading"
          :loading="subLoading"
          >Confirm</el-button
        >
      </div>
    </el-dialog>

         <el-dialog
      :title="upload.title"
      :visible.sync="upload.open"
      width="400px"
      append-to-body
    >
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">Please drag the file here,<em>or click to upload.</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" />
            Whether to update existing user data
          </div>
          <span>Only xls and xlsx format files are allowed for import.</span>
          <el-link
            type="primary"
            :underline="false"
            style="font-size: 12px; vertical-align: baseline"
            @click="importTemplate"
            >Download template</el-link
          >
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button
          type="primary"
          @click="submitFileForm"
          :disabled="subLoading"
          :loading="subLoading"
          >Confirm</el-button
        >
        <el-button
          @click="upload.open = false"
          :disabled="subLoading"
          :loading="subLoading"
          >Cancel</el-button
        >
      </div>
    </el-dialog>
    <el-dialog
      :close-on-click-modal="true"
      title="Delete Confirmation"
      :visible.sync="confirmRemoveVisible"
      width="520px"
      append-to-body
    >
      <div class="diaglog-remove-icon"><i class="el-icon-warning"></i></div>
      <div class="dialog-p">{{ removeTip }}</div>
      <div slot="footer" class="dialog-footer">
        <el-button
          @click="confirmRemoveVisible = false"
          :disabled="subLoading"
          :loading="subLoading"
          >Cancel</el-button
        >
        <el-button
          type="primary"
          @click="remove"
          :disabled="subLoading"
          :loading="subLoading"
          >Confirm</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listUser,
  getUser,
  delUser,
  addUser,
  updateUser,
  resetUserPwd,
  changeUserStatus,
  deptTreeSelect,
  checkUsernameOrEmail,
} from "@/api/system/user";
import { getToken } from "@/utils/auth";
import { table } from "@/mixins/table";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { defineComponent, reactive } from "vue";
import { deleteMember } from "@/api/system/projectMembers";

export default {
  name: "User",
  dicts: ["sys_normal_disable", "sys_user_sex"],
  components: { Treeselect },
  mixins: [table],
  data() {
    const checkUserName = (rele, value, cb) => {
      const reg = /^[a-zA-Z0-9_@-]{2,20}$/;
      if (reg.test(value)) {
        cb();
      } else {
        cb(new Error("Only allow letters, numbers, '', '@', '-' with a length of 2-20 characters."));
      }
    };
    const userNameCheck = (rule, value, callback) => {
      checkUsernameOrEmail({ userName: value, id: this.form.userId }).then(
        (res) => {
          if (!res.data) {
            callback();
          } else {
            callback(new Error("The username is already taken. Please enter a different username."));
          }
        }
      );
    };
    const emailCheck = (rule, value, callback) => {
      checkUsernameOrEmail({ email: value, id: this.form.userId }).then(
        (res) => {
          if (!res.data) {
            callback();
          } else {
            callback(new Error("The email is already linked to an account. Please enter a different email."));
          }
        }
      );
    };
    const phoneCheck = (rule, value, callback) => {
      checkUsernameOrEmail({ phone: value, id: this.form.userId }).then(
        (res) => {
          if (!res.data) {
            callback();
          } else {
            callback(new Error("The phone number is already linked to an account. Please enter a different phone number."));
          }
        }
      );
    };
    const checkPassword = (rule, value, cb) => {
      const illegalCharReg = /[^0-9a-zA-Z`~!@#$^&*()=|{}':;',.<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？\\\[\]]/;
      if (illegalCharReg.test(value)) {
        cb(new Error("It contains illegal characters. It can only include numbers, uppercase letters, lowercase letters, and special characters."));
        return;
      }
      let count = 0;
      if (/\d/.test(value)) {
        count++;
      }
      if (/[a-z]/.test(value)) {
        count++;
      }
      if (/[A-Z]/.test(value)) {
        count++;
      }
      if (
        /[`~!@#$^&*()=|{}':;',.<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？\\\[\]]/.test(
          value
        )
      ) {
        count++;
      }
      if (count > 1 && value.length >= 6 && value.length <= 20) {
        cb();
      } else {
        cb(
          new Error(
            "It must contain at least two of the following: numbers, uppercase letters, lowercase letters, and special characters. Additionally, the length should be between 6 and 20 characters."
          )
        );
      }
    };
    return {
      allAlign: null,
      confirmRemoveVisible: false,
      removeTip: "",
      getLoading: false,
      subLoading: false,
      loading: true,
      ids: [],
      staffIds: [],
      usernames: [],
      multiple: true,
      showSearch: true,
      total: 0,
      userList: null,
      title: "",
      deptOptions: undefined,
      open: false,
      deptName: undefined,
      initPassword: undefined,
      dateRange: [],
      postOptions: [],
      roleOptions: [],
      form: {
        userId: undefined,
        deptId: null,
        userName: undefined,
        nickName: undefined,
        password: undefined,
        phonenumber: undefined,
        email: undefined,
        sex: undefined,
        status: "0",
        remark: undefined,
        postIds: [],
        roleIds: [],
      },
      defaultProps: {
        children: "children",
        label: "label",
      },
      upload: {
        open: false,
        title: "",
        isUploading: false,
        updateSupport: 0,
        headers: { Authorization: "Bearer " + getToken() },
        url: process.env.VUE_APP_BASE_API + "/system/user/importData",
      },
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: "",
        phonenumber: "",
        email:"",
        status: undefined,
        deptId: undefined,
      },
      columns: [
        { key: 0, label: `Number`, visible: true },
        { key: 1, label: `Username`, visible: true },
        { key: 2, label: `Nickname`, visible: true },
        { key: 3, label: `Dept Name`, visible: true },
        { key: 4, label: `Phone Number`, visible: true },
        { key: 5, label: `Status`, visible: true },
        { key: 6, label: `Create Time`, visible: true },
      ],
      rules: {
        userName: [
          { validator: checkUserName, trigger: "blur" },
          { validator: userNameCheck, trigger: "blur" },
          { required: true, message: "The username cannot be empty.", trigger: "blur" },
          {
            min: 2,
            max: 20,
            message: "Only allow letters, numbers, '', '@', '-' with a length of 2-20 characters.",
            trigger: "blur",
          },
        ],
        nickName: [
          { required: true, message: "The user nickname cannot be empty.", trigger: "blur" },
        ],
        password: [
          { required: true, trigger: "blur", message: "The user password cannot be empty." },
          {
            validator: checkPassword,
            trigger: "blur",
          },
        ],
        email: [
          { validator: emailCheck, trigger: "blur" },
          {
            type: "email",
            message: "Please enter a valid email address.",
            trigger: ["blur", "change"],
          },
        ],
        phonenumber: [
          { validator: phoneCheck, trigger: "blur" },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "Please enter a valid phone number.",
            trigger: "blur",
          },
        ],
        roleIds: [
          { required: true, message: "The role information cannot be empty.", trigger: "change" },
        ],
      },
      tablePage: {
        currentPage: 1,
        pageSize: 10,
        totalResult: 100,
      },
    };
  },
  watch: {
    deptName(val) {
      this.$refs.tree.filter(val);
    },
  },
  created() {
    this.getList();
    this.getDeptTree();
    this.getConfigKey("sys.user.initPassword").then((response) => {
      this.initPassword = response.msg;
    });
  },
  mounted() {
    this.dragControllerDiv();
  },
  methods: {
    getList() {
      this.queryParams.pageNum = this.tablePage.currentPage;
      this.queryParams.pageSize = this.tablePage.pageSize;
      this.loading = true;
      listUser(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.userList = response.rows;
          this.tablePage.totalResult = response.total;
          this.loading = false;
        }
      );
    },
    getDeptTree() {
      deptTreeSelect().then((response) => {
        this.deptOptions = response.data
      })
    },
    my_normalizer(node) {
      let text = node.label + ' (Disabled) '
      return {
        id: node.id,
        label: node.status == 1 ? text : node.label,
        children: node.children,
        isDisabled: node.status == 1 ? true : false
      };
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    handleNodeClick(data) {
      if(this.queryParams.deptId == data.id){
        this.queryParams.deptId = null
        this.getDeptTree()
      }else{
        this.queryParams.deptId = data.id
      }
      this.handleQuery()
    },
    handleStatusChange(row) {
      const text = row.status === "0" ? "Enable" : "Disable";
      this.$modal
        .confirm('Do you confirm to"' + text + '" user  "' + row.userName + '"?')
        .then(function () {
          return changeUserStatus(row.userId, row.status);
        })
        .then(() => {
          this.$modal.msgSuccess(text + "successful");
        })
        .catch(function () {
          row.status = row.status === "0" ? "1" : "0";
        });
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    reset() {
      this.form = {
        userId: undefined,
        deptId: null,
        userName: "",
        nickName: undefined,
        password: undefined,
        phonenumber: "",
        email: undefined,
        sex: undefined,
        status: "0",
        remark: undefined,
        postIds: [],
        roleIds: [],
      };
      this.resetForm("form");
    },
    handleQuery() {
      this.queryParams.userName = this.queryParams.userName.trim();
      this.queryParams.phonenumber = this.queryParams.phonenumber.trim();
      this.queryParams.email = this.queryParams.email.trim();
      this.tablePage.currentPage = 1
      this.getList();
    },
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleCommand(command, row) {
      switch (command) {
        case "handleResetPwd":
          this.handleResetPwd(row);
          break;
        case "handleAuthRole":
          this.handleAuthRole(row);
          break;
        default:
          break;
      }
    },
    handleAdd() {
      this.reset();
      getUser().then((response) => {
        this.postOptions = response.posts;
        this.roleOptions = response.roles;
        this.open = true;
        this.title = "Add user";
        //this.form.password = this.initPassword
      });
    },
    handleUpdate(row) {
      this.reset();
      const userId = row.userId;
      this.open = true;
      this.getLoading = true;
      getUser(userId)
        .then((response) => {
          this.form = response.data;
          this.postOptions = response.posts;
          this.roleOptions = response.roles;
          this.form.postIds = response.postIds;
          this.form.roleIds = response.roleIds;
          this.title = "Modify user";
          this.form.password = "";
        })
        .finally(() => {
          this.getLoading = false;
        });
    },
    handleResetPwd(row) {
      this.$prompt('Please enter"' + row.userName + '" the new password', "Hint", {
        inputType: "text",
        confirmButtonText: "Confirm",
        cancelButtonText: "Cancel",
        closeOnClickModal: false,
        // inputValue:'',
        inputValidator: (value) => {
          let count = 0;
          let num = 0;
          if (!value) {
            return "The input cannot be empty.";
          }
          const illegalCharReg = /[^0-9a-zA-Z`~!@#$^&*()=|{}':;',.<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？\\\[\]]/; 
          if (illegalCharReg.test(value)) {
            return "It contains illegal characters. It can only include numbers, uppercase letters, lowercase letters, and special characters.";
          }

          if (/\d/.test(value)) {
            count++;
          }
          if (/[a-z]/.test(value)) {
            count++;
          }
          if (/[A-Z]/.test(value)) {
            count++;
          }
          if (
            /[`~!@#$^&*()=|{}':;',.<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？\\\[\]]/.test(
              value
            )
          ) {
            count++;
          }
          if (count >= 2 && value.length >= 6 && value.length <= 20) {
            num++;
          }
          return num == 1 || count == 0;
        },
        inputErrorMessage:
          "It must contain at least two of the following: numbers, uppercase letters, lowercase letters, and special characters. Additionally, the length should be between 6 and 20 characters.",
      })
        .then(({ value }) => {
          resetUserPwd(row.userId, value).then((response) => {
            this.$modal.msgSuccess("Modification successful!");
          });
        })
        .catch(() => {});
    },
    handleAuthRole: function (row) {
      const userId = row.userId;
      this.$router.push("/system/user-auth/role/" + userId);
    },
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.subLoading = true;
          if (this.form.userId) {
            updateUser(this.form)
              .then((response) => {
                this.$modal.msgSuccess("Modification successful!");
                this.open = false;
                this.getList();
              })
              .finally(() => {
                this.subLoading = false;
              });
          } else {
            addUser(this.form)
              .then((response) => {
                this.$modal.msgSuccess("Addition successful!");
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
      this.confirmRemoveVisible = true
      if(row.userId){
        this.ids=row.userId
      }
      this.staffIds = this.ids;
      this.removeTip =
        'Are you sure you want to delete the user with the name "' + this.usernames + '" ?';
    },
    handleDelete1(row) {
      this.confirmRemoveVisible = true;
      if (row.userId) {
        this.ids = row.userId;
        let arr = [];
        arr.push(row.userId);
        this.staffIds = arr;
      }
      this.removeTip = 'Are you sure you want to delete the user with the name "' + row.userName + '" ?';
    },
    remove() {
      this.subLoading = true;
      delUser(this.ids)
        .then((res) => {
          this.getList();
          this.$modal.msgSuccess("Deletion successful!");
          deleteMember(this.staffIds);
        })
        .finally(() => {
          this.subLoading = false;
          this.confirmRemoveVisible = false;
        });
    },
    dataChange() {
      if (this.dateRange == null) {
        this.getList();
      }
    },
    handleExport() {
      (this.queryParams.ids = this.ids),
        this.download(
          "system/user/export",
          {
            ...this.queryParams,
          },
          `user_${new Date().getTime()}.xlsx`
        );
    },
    handleImport() {
      this.upload.title = "User import";
      this.upload.open = true;
    },
    importTemplate() {
      this.download(
        "system/user/importTemplate",
        {},
        `user_template_${new Date().getTime()}.xlsx`
      );
    },
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(
        "<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" +
          response.msg +
          "</div>",
        "Import result",
        { dangerouslyUseHTMLString: true }
      );
      this.getList();
    },
    submitFileForm() {
      this.$refs.upload.submit();
    },
    selectChangeEvent({ checked }) {
      const records = this.$refs.SysUserList.getCheckboxRecords();
      let arr = [];
      let arr1 = [];
      for (let item of records) {
        arr.push(item.userId);
        arr1.push(item.userName);
      }
      this.ids = arr;
      this.usernames = arr1;
      if (records && records.length) {
        this.multiple = false;
      } else {
        this.multiple = true;
      }
    },
    getSelectEvent() {
      let selectRecords = this.$refs.SysUserList.getCheckboxRecords();
      VXETable.modal.alert(selectRecords.length);
    },
    pageChangeEvent({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getList();
    },
    totalShow() {
      let total = this.tablePage.totalResult;
      let str = "Total ";
      let query = this.queryParams;
      if (
        query.projectName ||
        (query.createStaffNoList && query.createStaffNoList.length != 0)
      ) {
        str = "found ";
      }
      return str + total + " items";
    },
    dragControllerDiv: function () {
      var resize = document.getElementsByClassName("resize");
      var left = document.getElementsByClassName("sys-user-flex-left");
      var mid = document.getElementsByClassName("sys-user-flex-right");
      var box = document.getElementsByClassName("sys-user");
      for (let i = 0; i < resize.length; i++) {
        resize[i].onmousedown = function (e) {
          resize[i].style.background = "#e8e9ed";
          var startX = e.clientX;
          resize[i].left = resize[i].offsetLeft;
          document.onmousemove = function (e) {
            var endX = e.clientX;
            var moveLen = resize[i].left + (endX - startX);
            var maxT = box[i].clientWidth - resize[i].offsetWidth;

            if (moveLen < 100) moveLen = 100;
            if (moveLen > maxT - window.innerWidth * 0.5)
              moveLen = maxT - window.innerWidth * 0.5;

            resize[i].style.left = moveLen;

            for (let j = 0; j < left.length; j++) {
              left[j].style.width = moveLen + "px";
              mid[j].style.width = box[i].clientWidth - moveLen - 10 + "px";
            }
          };
          document.onmouseup = function (evt) {
            resize[i].style.background = "#e8e9ed";
            document.onmousemove = null;
            document.onmouseup = null;
            resize[i].releaseCapture && resize[i].releaseCapture();
          };
          resize[i].setCapture && resize[i].setCapture();
          return false;
        };
      }
    },
  },
};
</script>
<style lang="scss" scoped>
@import "@/assets/styles/variables.scss";
.table-f {
  height: calc(75vh - 173px);
}
p {
    margin-bottom: 0px;
    margin-top: 0;
}
.sys-user {
  padding: 20px;
  height: calc(100vh - #{$top-bar-height});
  .sys-user-flex {
    display: flex;
    height: 100%;
    .sys-user-flex-left {
      width: calc(32% - 130px);
      height: 100%;
      background: #ffffff;
      float: left;
      background-color: #fff;
      padding: 20px;
    }
    .sys-user-flex-right {
      float: left;
      width: 68%;
      height: 100%;
      background: #fff;
      box-shadow: -1px 4px 5px 3px rgba(0, 0, 0, 0.11);
      flex: auto;
      display: flex;
      flex-direction: column;
    }
  }

  .search-form {
    background-color: #fff;
    padding: 18px 20px 0 20px;
  }
  .table-data {
    background-color: #fff;
    padding: 17px 20px;
    //margin-top: 20px;
    flex: auto;
  }
  .resize:hover {
    cursor: w-resize;
  }
  .showtree {
    ::v-deep .el-tree-node__content {
      display: block;
      width: calc(100% - 30px);
      overflow: hidden;
      white-space: nowrap;
      text-overflow: tooltip;
    }
  }
}
</style>
