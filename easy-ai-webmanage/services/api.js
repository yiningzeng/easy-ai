import request from '../utils/request';
import { stringify } from 'qs';

const ip="111.231.134.58:8088"; // "10.50.102.166";

export async function doLogin(params) {
    const formData = new FormData();
    formData.append('username', params.username);
    formData.append('password', params.password);
    return request(`http://${ip}/api/v1/user/login`, {
        method: 'POST',
        body:formData,
    });
}

export async function getUserList(params) {
    // console.log("getFishs"+JSON.stringify(params));/api/v1/user?page=0&size=10&sortField=username&sortType=desc&userType=0
    return request(`http://${ip}/api/v1/user?${stringify(params)}`, {
        headers: {authorization: sessionStorage.getItem("token")},
        method: 'GET',
    });
}

export async function getDownloadUrl(params) {
    // console.log("getFishs"+JSON.stringify(params));http://111.231.134.58:8088/api/v1/download/qt
    return request(`http://${ip}/api/v1/download/${params.user}`, {
        headers: {authorization: sessionStorage.getItem("token")},
        method: 'GET',
    });
}