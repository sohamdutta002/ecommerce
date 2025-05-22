const BASE_URL = "";

const apiClient = async (endpoint, { method = 'GET', headers = {}, body } = {}) => {
    const token = localStorage.getItem('token');

    const defaultHeaders = {
        'Content-Type': 'application/json',
        ...(token && { Authorization: `Bearer ${token}` })
    }

    try {
        const response = await fetch(`${BASE_URL}${endpoint}`, {
            method,
            headers: { ...defaultHeaders, ...headers },
            body: body ? JSON.stringify(body) : undefined,
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Something went wrong');
        }
        return await response.json();
    } catch (error) {
        console.log('API Error:', error.message);
// rethrow to the calling funciton then throw error;
    }
}

export default apiClient;