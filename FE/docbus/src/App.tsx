import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Home from './pages/Home';
import About from './pages/About';
import MarkdownEditor from './components/MarkDownEditor';

function App() {
  return (
    <Router>
      <nav className="p-4 bg-gray-200 flex justify-center gap-4">
        <Link to="/" className="text-blue-500 hover:underline">Home</Link>
        <Link to="/about" className="text-green-500 hover:underline">About</Link>
        <Link to="/editor" className="text-red-500 hover:underline">Markdown Editor</Link>
      </nav>

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/editor" element={<MarkdownEditor />} />
      </Routes>
    </Router>
  );
}

export default App;
