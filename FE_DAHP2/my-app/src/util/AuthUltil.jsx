import { jwtDecode } from "jwt-decode";

// Decode JWT token và check role
export function decodeTokenAndCheckRole(token) {
  if (!token) {
    return null;
  }

  let payload = null;

  try {
    // decode token sử dụng jwt-decode
    payload = jwtDecode(token);
  } catch (error) {
    // Trường hợp không decode được token bằng jwt-decode
    try {
      payload = JSON.parse(atob(token.split(".")[1]));
    } catch (error) {
      console.error("Error decoding token:", error);
      return null;
    }
  }

  // lấy name, roles, scope từ payload
  const name = payload?.name || payload?.username || "uknown";
  const scope = payload?.scope || "";

  const rolesArray = scope
    .split(",") // tách bằng dấu phẩy
    .map((r) => r.trim()) // loại bỏ khoảng trắng
    .filter(Boolean); // bỏ phần rỗng

  // kiểm tra roles
  const isAdmin =
    rolesArray.includes("ROLE_ADMIN") || rolesArray.includes("ADMIN_ROLE");
  const isUser =
    rolesArray.includes("ROLE_USER") || rolesArray.includes("USER_ROLE");

  return {
    name,
    username: payload.username,
    userId: payload.userId,
    roles: rolesArray,
    isAdmin,
    isUser,
    payload,
  };
}
