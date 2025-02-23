import { Button } from "@/components/ui/button";

export default function Home() {
  return (
    <div className="min-h-screen flex flex-col">
      {/* Header */}
      <header className="w-full p-4 bg-white shadow-md flex justify-between items-center">
        <h1 className="text-2xl font-bold text-indigo-600">Docbus</h1>
        <nav className="hidden md:flex gap-6">
          <a href="#features" className="text-gray-600 hover:text-indigo-600">Features</a>
          <a href="#about" className="text-gray-600 hover:text-indigo-600">About</a>
          <a href="#contact" className="text-gray-600 hover:text-indigo-600">Contact</a>
        </nav>
        <Button className="bg-indigo-600 text-white px-6 py-2 rounded-lg">Login</Button>
      </header>

      {/* Hero Section */}
      <section className="flex-1 flex flex-col items-center justify-center text-center p-10 bg-gradient-to-r from-indigo-100 to-blue-50">
        <h2 className="text-4xl font-bold text-indigo-700 mb-4">Your Documents, Simplified.</h2>
        <p className="text-gray-600 max-w-xl mb-6">Effortless document management with powerful tools and AI-driven insights.</p>
        <Button className="bg-indigo-600 text-white px-8 py-3 rounded-lg text-lg shadow-lg hover:scale-105 transition">Get Started</Button>
      </section>

      {/* Features Section */}
      <section id="features" className="p-12 bg-white text-center">
        <h3 className="text-3xl font-bold text-indigo-600 mb-6">Why Choose Docbus?</h3>
        <div className="grid md:grid-cols-3 gap-8">
          <div className="p-6 bg-gray-50 rounded-lg shadow-lg">
            <h4 className="text-xl font-semibold text-indigo-600">Cloud Storage</h4>
            <p className="text-gray-600 mt-2">Access your documents anywhere, anytime.</p>
          </div>
          <div className="p-6 bg-gray-50 rounded-lg shadow-lg">
            <h4 className="text-xl font-semibold text-indigo-600">AI Organization</h4>
            <p className="text-gray-600 mt-2">Smart categorization and quick search.</p>
          </div>
          <div className="p-6 bg-gray-50 rounded-lg shadow-lg">
            <h4 className="text-xl font-semibold text-indigo-600">Collaboration</h4>
            <p className="text-gray-600 mt-2">Seamless team collaboration and sharing.</p>
          </div>
        </div>
      </section>

      {/* About Section */}
      <section id="about" className="p-12 bg-gray-100 text-center">
        <h3 className="text-3xl font-bold text-indigo-600 mb-6">About Us</h3>
        <p className="text-gray-600 max-w-2xl mx-auto">Docbus is dedicated to making document management easy, secure, and accessible for everyone.</p>
      </section>

      {/* Contact Section */}
      <section id="contact" className="p-12 bg-white text-center">
        <h3 className="text-3xl font-bold text-indigo-600 mb-6">Contact Us</h3>
        <p className="text-gray-600">Have any questions? Reach out to us at <a href="mailto:support@docbus.com" className="text-indigo-600">support@docbus.com</a>.</p>
      </section>

      {/* Footer */}
      <footer className="w-full p-6 bg-gray-800 text-white text-center">
        <p>&copy; {new Date().getFullYear()} Docbus. All rights reserved.</p>
      </footer>
    </div>
  );
}
