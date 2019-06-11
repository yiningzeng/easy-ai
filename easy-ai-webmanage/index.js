import React from 'react';
import ReactDOM from 'react-dom';
import dva, { connect } from 'dva';
import { Card, Drawer, Dropdown, Menu, Col, Table, message, Divider, Icon,Spin ,Button,Select} from 'antd';
// 由于 antd 组件的默认文案是英文，所以需要修改为中文
import zhCN from 'antd/lib/locale-provider/zh_CN';
import moment from 'moment';
import 'moment/locale/zh-cn';
import { doLogin, getUserList, getDownloadUrl } from './services/api';
import Iframe from 'react-iframe';

moment.locale('zh-cn');

class FreeFish extends React.Component {
    state = {
        selectedRowKeys: [], // Check here to configure the default column
        pagination: {defaultPageSize:100},
        loading: false,
        visible: false,
        ftpUrl: "ftp://111.231.134.58:21/"
    };

    onSelectChange = (selectedRowKeys) => {
        console.log('selectedRowKeys changed: ', selectedRowKeys);
        this.setState({ selectedRowKeys });
    };

    componentDidMount() {
        // try{
        //     const tableCon = ReactDOM.findDOMNode(this.refs['table']);
        //     const table = tableCon.querySelector('table');
        //     table.setAttribute('id','table-to-xls');
        // }
        // catch (e) {
        //     // console.error(e);
        // }
        const {dispatch} = this.props;
        message.success(`正在加载`);
        dispatch({
            type: 'service/doLogin',
            payload: {
              username: "baymin",
              password: "e10adc3949ba59abbe56e057f20f883e",
            },
            callback: (v) => {
                sessionStorage.setItem("token", v.data.token);
                // message.success(v.data.token);
                dispatch({
                    type: 'service/getUserList',
                    payload: {
                        page: 0,
                        size: 100,
                        sortField: "username",
                        sortType: "desc",
                        userType: 0,
                    },
                });
            }
          });
        // dispatch({
        //     type: 'service/getAppointKey',
        //     callback: (v) => {
        //         dispatch({
        //             type: 'service/getFishs',
        //             payload: {
        //                 showTip: 0,
        //                 page: 0,
        //                 size: 100,
        //             },
        //             callback: (v) => {
        //                 this.setState({
        //                     ...this.state,
        //                     pagination:{
        //                         ...this.state.pagination,
        //                         total: v["data"]["totalElements"]
        //                     }
        //                 });
        //                 console.log(`${JSON.stringify(v)}`);
        //         },
        //         });
        //     }
        // });
        // dispatch({
        //     type: 'service/getFishs',
        //     payload: {
        //         page: 0,
        //         size: 10,
        //     },
        //     callback: (v) => {
        //         console.log(`服务开启状态:${v}`);
        //         message.success(JSON.stringify(v));
            // },
        // });
    }

    handleTableChange = (pagination, filters, sorter) => {
        const pager = { ...this.state.pagination };
        pager.current = pagination.current;
        this.setState({
            pagination: pager,
        });

        const {dispatch} = this.props;
        console.log("filters"+filters.appointKey.length+" !"+JSON.stringify(filters));
        dispatch({
            type: 'service/getFishs',
            payload: {
                showTip: 0,
                appointKeyId: filters.appointKey.length>0?filters.appointKey[0]:undefined,
                page: pager.current-1,
                size: 100,
            },
            callback: (v) => {
                this.setState({
                    ...this.state,
                    pagination:{
                        ...this.state.pagination,
                        total: v["data"]["totalElements"]
                    }
                });
                console.log(`${JSON.stringify(v)}`);
            },
        });
        // this.fetch({
        //     results: pagination.pageSize,
        //     page: pagination.current,
        //     sortField: sorter.field,
        //     sortOrder: sorter.order,
        //     ...filters,
        // });
    };

    render() {
        const {
            service: { list: { data } }
        } = this.props;

        // message.success(JSON.stringify(data.content));
        // let filters = keylist.data.map(function(item){
        //         return {text: `[${item["status"]===1?"开启":"关闭"}] ${item["key"]}`, value: item["id"]};
        // });

        const columns = [{
            title: '公司',
            dataIndex: 'relName',
        }, {
            title: '用户',
            dataIndex: 'username',
        }, {
            title: '最后上传数据时间',
            dataIndex: 'createTime',
        }, {
            title: '图片数据',
            key: 'action',
            // dataIndex: 'ftpUrl',
            render: record => (
            <span>
                <a href={record.ftpUrl} onClick = {()=>{
                    // this.setState({
                    //     visible: true,
                    //     ftpUrl: record.ftpUrl
                    // });
                }}  target="_Blank">打开</a>
                <Divider type="vertical" />
                <a href="javascript:;" onClick={()=>{
                    const {dispatch} = this.props;
                    dispatch({
                        type: 'service/getDownloadUrl',
                        payload: {
                            user: record.username,
                        },
                        callback: (v) => {
                            // message.success(`${JSON.stringify(v)}`);
                            window.location.href= v.data;
                            // window
                            // window.open(v.data, '_blank').location;
                        },
                    });
                }}>打包下载最新的数据</a>
            </span>)
        }];


        // const { selectedRowKeys } = this.state;
        // const rowSelection = {
        //     selectedRowKeys,
        //     onChange: this.onSelectChange,
        //     hideDefaultSelections: true,
        //     selections: [{
        //         key: 'all-data',
        //         text: 'Select All Data',
        //         onSelect: () => {
        //             this.setState({
        //                 selectedRowKeys: [...Array(46).keys()], // 0...45
        //             });
        //         },
        //     }, {
        //         key: 'odd',
        //         text: 'Select Odd Row',
        //         onSelect: (changableRowKeys) => {
        //             let newSelectedRowKeys = [];
        //             newSelectedRowKeys = changableRowKeys.filter((key, index) => {
        //                 if (index % 2 !== 0) {
        //                     return false;
        //                 }
        //                 return true;
        //             });
        //             this.setState({ selectedRowKeys: newSelectedRowKeys });
        //         },
        //     }, {
        //         key: 'even',
        //         text: 'Select Even Row',
        //         onSelect: (changableRowKeys) => {
        //             let newSelectedRowKeys = [];
        //             newSelectedRowKeys = changableRowKeys.filter((key, index) => {
        //                 if (index % 2 !== 0) {
        //                     return true;
        //                 }
        //                 return false;
        //             });
        //             this.setState({ selectedRowKeys: newSelectedRowKeys });
        //         },
        //     }],
        //     onSelection: this.onSelection,
        // };
        return (
            <Card>
                <Drawer
          // style={{position: "relative", zIndex: 998,}}
          placement="left"
          height='100%'
          width='100%'
          mask={false}
          closable={false}
          onClose={() => {
          }}
          visible={this.state.visible}
        >
          <Card
            bordered={false}
            title="文件"
            extra={
              <Dropdown.Button type="danger" size="large"
                               onClick={() => {
                                 this.setState({visible: false});
                                 // routerRedux.push(sessionStorage.getItem("oldPath"));
                               }}
                               overlay={
                                 <Menu onClick={() => {
                                   this.setState({visible: false});
                                 }}>
                                   <Menu.Item key="1"><Icon type="export"/></Menu.Item>
                                 </Menu>
                               }>退出</Dropdown.Button>
            }
          >
              <iframe src="ftp://qtvision:qtvision1024@111.231.134.58:21/"></iframe>
            {/* <Iframe url="ftp://111.231.134.58:21/"
                    width="100%"
                    height="800px"
                    id="myId"
                    className="myClassname"
                    style={{overflow:"hidden",frameborder: "no",border: "0",marginwidth: "0",marginheight: "0",scrolling:"no"}}
                    display="initial"
                    position="relative"/> */}
          </Card>
        </Drawer>
                <Table size={"small"} columns={columns} dataSource={data.content} onChange={this.handleTableChange}
                //    expandedRowRender={record => {
                //        return(
                //            <Row>
                //                <Row>
                //                    <Button type="primary" size="small" style={{marginLeft: 10}}>Primary</Button>
                //                    <Button type="primary" size="small" style={{marginLeft: 10}}>Primary</Button>
                //                    <Button type="primary" size="small" style={{marginLeft: 10}}>Primary</Button>
                //                </Row>
                //                <Row>
                //                    <Divider/>
                //                </Row>
                //                <Row>
                //                    {/* <a href={record.url} target="view_window" style={{ margin: 0 }}>{record.description}</a> */}
                //                </Row>
                //            </Row>
                //        )
                //    }}
                //    expandRowByClick
                   pagination={this.state.pagination}/>
            </Card>
        );
    }
}

// 1. Initialize
const app = dva();
console.log(2);
// 2. Model
// app.model(require('./src/models/service').default);
app.model({
    namespace: 'service',
    state: {
        res: {
            code: undefined,
            status: undefined,
            msg: '',
            data: [],
        },
        list: {
            code: undefined,
            status: undefined,
            msg: '',
            data: {},
        },
    },
    effects: {
        *doLogin({ payload,callback}, { call, put }) {
            const response = yield call(doLogin,payload);
            yield put({
                type: 'res',
                payload: response,
            });
            if (callback)callback(response);
        }, 
        *getUserList({ payload,callback}, { call, put }) {
            const response = yield call(getUserList,payload);
            yield put({
                type: 'list',
                payload: response,
            });
            if (callback)callback(response);
        },
        *getDownloadUrl({ payload,callback}, { call, put }) {
            const response = yield call(getDownloadUrl,payload);
            yield put({
                type: 'res',
                payload: response,
            });
            if (callback)callback(response);
        },
    },
    reducers: {
        res(state, action) {
            return {
                ...state,
                res: action.payload,
            };
        },
        list(state, action) {
            return {
                ...state,
                list: action.payload,
            };
        },
    },
});
// 3. View
const App = connect(({ service }) => ({
    service
}))(function(props) {
    const { dispatch } = props;
    return (
        <div>
            <FreeFish {...props}/>
        </div>
    );
});

// 4. Router
app.router(() => <App />);

// 5. Start
app.start('#root');



// ReactDOM.render(<App />, document.getElementById('root'));
