import axios from 'axios'
import Utils from '../Utils/Utils'
const API_URL = 'http://localhost:8081/api/v1'
const AUTH_URL = 'http://localhost:8081/auth'
class BackendService {
    login(login, password) {
        return axios.post(`${AUTH_URL}/login`, { login, password })
    }
    logout() {
        return axios.get(`${AUTH_URL}/logout`, { headers: { Authorization: Utils.getToken() } })
    }

    /* Countries */
    retrieveAllCountries(page, limit) {
        return axios.get(`${API_URL}/countries?page=${page}&limit=${limit}`, {headers: { Authorization: Utils.getToken() } });
    }
    retrieveCountry(id) {
        return axios.get(`${API_URL}/countries/${id}`, {headers: { Authorization: Utils.getToken() } });
    }
    createCountry(country) {
        return axios.post(`${API_URL}/countries`, country, {headers: { Authorization: Utils.getToken() } });
    }
    updateCountry(country) {
        return axios.put(`${API_URL}/countries/${country.id}`, country, {headers: { Authorization: Utils.getToken() } });
    }
    deleteCountries(countries) {
        return axios.post(`${API_URL}/deletecountries`, countries, {headers: { Authorization: Utils.getToken() } });
    }
    /* User */
    retrieveUser(id) {
        return axios.get(`${API_URL}/users/${id}`, {headers: { Authorization: Utils.getToken() } });
    }
    updateUser(user) {
        return axios.put(`${API_URL}/users/${user.id}`, user, {headers: { Authorization: Utils.getToken() } });
    }

}
export default new BackendService()