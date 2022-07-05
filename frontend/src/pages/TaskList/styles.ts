import { css } from '@emotion/react';

const layout = css`
  min-height: 100vh;
  padding: 8px 32px;
`;

const contents = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: center;
`;

const location = css`
  box-sizing: border-box;
  margin: 16px;
`;

const locationName = css`
  font-size: 20px;
  font-weight: 600;
  margin: 16px 8px;
`;

const styles = { layout, contents, location, locationName };

export default styles;
