require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "rn-system-brightness"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
  is a small and simple package that helps make your React Native app
                   DESC
  s.homepage     = "https://github.com/hoanglam10499/rn-system-brightness"
  # brief license entry:
  s.license      = "MIT"
  # optional - use expanded license entry instead:
  # s.license    = { :type => "MIT", :file => "LICENSE" }
  s.authors      = { "Hoang Lam" => "hoanglam10499@gmail.com" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/hoanglam10499/rn-system-brightness.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,c,m,swift}"
  s.requires_arc = true

  s.dependency "React"
  # ...
  # s.dependency "..."
end

