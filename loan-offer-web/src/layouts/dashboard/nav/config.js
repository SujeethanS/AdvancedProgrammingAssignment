// component
import SvgColor from '../../../components/svg-color';

// ----------------------------------------------------------------------

const icon = (name) => <SvgColor src={`/assets/icons/navbar/${name}.svg`} sx={{ width: 1, height: 1 }} />;

const navConfig = [
  {
    title: 'dashboard',
    path: '/dashboard/app',
    icon: icon('ic_analytics'),
  },
  {
    title: 'user',
    path: '/dashboard/user',
    icon: icon('ic_user'),
  },
  {
    title: 'product',
    path: '/dashboard/products',
    icon: icon('ic_cart'),
  },
  {
    title: 'category',
    path: '/dashboard/category',
    icon: icon('ic_cart'),
  },
  {
    title: 'order',
    path: '/dashboard/order',
    icon: icon('ic_cart'),
  },
  {
    title: 'payment',
    path: '/dashboard/payment',
    icon: icon('ic_cart'),
  },
  {
    title: 'orderPayment',
    path: '/dashboard/orderpayment',
    icon: icon('ic_cart'),
  },
  {
    title: 'customerPayment',
    path: '/dashboard/customerpayment',
    icon: icon('ic_cart'),
  }
  // {
  //   title: 'blog',
  //   path: '/dashboard/blog',
  //   icon: icon('ic_blog'),
  // },
  // {
  //   title: 'login',
  //   path: '/login',
  //   icon: icon('ic_lock'),
  // },
  // {
  //   title: 'Not found',
  //   path: '/404',
  //   icon: icon('ic_disabled'),
  // },
];

export default navConfig;
