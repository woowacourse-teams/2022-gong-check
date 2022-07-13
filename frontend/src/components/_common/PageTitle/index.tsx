import { css } from '@emotion/react';

interface PageTitleProps {
  children: React.ReactNode;
}

const PageTitle: React.FC<PageTitleProps> = ({ children }) => {
  return (
    <p
      css={css`
        margin: 24px 0;
        font-size: 20px;
        font-weight: 600;
      `}
    >
      {children}
    </p>
  );
};

export default PageTitle;
