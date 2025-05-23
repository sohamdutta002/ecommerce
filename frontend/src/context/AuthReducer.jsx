const savedUser = JSON.parse(localStorage.getItem("user"));
const savedToken = localStorage.getItem("token");

export const initialAuthState = {
    isAuthenticated: !!savedUser,
    user: savedUser || null,
    token: savedToken || null,
};

export const authReducer = (state, action) => {
    switch (action.type) {
        case "LOGIN":
            const newState = {
                isAuthenticated: true,
                user: action.payload.user,
                token: action.payload.token,
            };
            // console.log(newState);
            return newState;
        case "LOGOUT":
            return {
                isAuthenticated: false,
                user: null,
                token: null,
            };
        default:
            return state;
    }
}