# Empty puppet manifest.
# Add config here to use your modules managed by librarian-puppet.

class { 'locales':
  default_locale  => 'nb_NO.UTF-8',
  locales         => ['nb_NO.UTF-8 UTF-8', 'en_US.UTF-8 UTF-8', 'en_GB.UTF-8 UTF-8'],
}

->

class { 'postgresql::globals':
  manage_package_repo => true,
  version             => '9.3',
}

->

class { 'postgresql::server':
  listen_addresses           => '*',
  ip_mask_allow_all_users => '0.0.0.0/0',
  encoding => 'UTF8',
  locale => 'nb_NO.UTF-8'
}

->

postgresql::server::db { 'scala-skall':
  user     => 'scala-skall',
  password => 'scala-skall',
}

