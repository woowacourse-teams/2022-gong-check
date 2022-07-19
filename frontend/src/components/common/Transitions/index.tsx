import { cloneElement } from 'react';
import { TransitionGroup, CSSTransition } from 'react-transition-group';

interface TransitionsProps {
  transition: string;
  pageKey: string;
  children: React.ReactNode;
}

const childFactoryCreator = (props: { classNames: string }) => (child: React.ReactElement) =>
  cloneElement(child, props);

const Transitions: React.FC<TransitionsProps> = ({ transition = 'right', pageKey, children }) => (
  <TransitionGroup className="transitions-group" childFactory={childFactoryCreator({ classNames: transition })}>
    <CSSTransition key={pageKey} timeout={500}>
      {children}
    </CSSTransition>
  </TransitionGroup>
);

export default Transitions;
