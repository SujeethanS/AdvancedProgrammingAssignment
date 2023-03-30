import { Navigate, useRoutes } from 'react-router-dom';
// layouts
import DashboardLayout from './layouts/dashboard';
import SimpleLayout from './layouts/simple';
//
import BlogPage from './pages/BlogPage';
import UserPage from './pages/UserPage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import Page404 from './pages/Page404';
import ProductPage from './pages/ProductPage';
import DashboardAppPage from './pages/DashboardAppPage';
import CategoryPage from './pages/CategoryPage';
import PaymentPage from './pages/PaymentPage';
import OrderPage from './pages/OrderPage';
import OrderPaymentPage from './pages/OrderPaymentPage';
import CustomerPaymentPage from './pages/CustomerPaymentPage';

// ----------------------------------------------------------------------

export default function Router() {
  const routes = useRoutes([
    {
      path: '/dashboard',
      element: <DashboardLayout />,
      children: [
        { element: <Navigate to="/dashboard/app" />, index: true },
        { path: 'app', element: <DashboardAppPage /> },
        { path: 'user', element: <UserPage /> },
        { path: 'products', element: <ProductPage /> },
        { path: 'category', element: <CategoryPage /> },
        { path: 'order', element: <OrderPage /> },
        { path: 'payment', element: <PaymentPage /> },
        { path: 'orderpayment', element: <OrderPaymentPage /> },
        { path: 'customerpayment', element: <CustomerPaymentPage /> }

        // { path: 'blog', element: <BlogPage /> },
      ],
    },
    {
      path: 'login',
      element: <LoginPage />,
    },
    {
      path: 'register',
      element: <RegisterPage />,
    },
    {
      element: <SimpleLayout />,
      children: [
        { element: <Navigate to="/login" />, index: true },
        { path: '404', element: <Page404 /> },
        { path: '*', element: <Navigate to="/404" /> },
      ],
    },
    {
      path: '*',
      element: <Navigate to="/404" replace />,
    },
  ]);

  return routes;
}
