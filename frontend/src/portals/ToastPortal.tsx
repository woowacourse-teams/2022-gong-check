import ReactDOM from 'react-dom';

interface ToastPortalProps {
  children: React.ReactNode;
}

const ToastPortal = ({ children }: ToastPortalProps) => {
  return ReactDOM.createPortal(children, document.getElementById('toast') as HTMLElement);
};

export default ToastPortal;
