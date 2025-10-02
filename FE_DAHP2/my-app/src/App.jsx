import './App.css'
import { Routers } from './routes/routes'
import { useRoutes } from 'react-router-dom'


function App() {
  const routing = useRoutes(Routers)
  return (
    <>
      {routing}
    </>
  )
}

export default App
