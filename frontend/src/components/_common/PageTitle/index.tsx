/**  @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

type PageTitleProps = {
  children: React.ReactNode;
};

const PageTitle = ({ children }: PageTitleProps) => {
  return (
    <p
      css={css`
        font-size: 20px;
        font-weight: 600;
      `}
    >
      {children}
    </p>
  );
};

export default PageTitle;
