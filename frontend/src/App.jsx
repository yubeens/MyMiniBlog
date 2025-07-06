import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Layout from "./components/Layout";
import LoginPage from './page/LoginPage';
import PostsPage from './page/PostsPage';
import HomePage from "./page/HomePage";
import TodoPage from "./page/TodoPage";
import PostCreatePage from "./page/PostCreatePage";
import RegisterPage from "./page/RegisterPage";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route path="/" element={<Layout><HomePage /></Layout>} />
                <Route path="/posts" element={<Layout><PostsPage /></Layout>} />
                <Route path="/todos" element={<Layout><TodoPage /></Layout>} />
                <Route path="/posts/new" element={<PostCreatePage />} />
            </Routes>
        </Router>
    )
}
export default App;